package com.loposition.util.express.trash;

import com.loposition.util.express.trash.context.DictServiceHolder;
import com.loposition.util.express.trash.exception.ArrayParseException;
import com.loposition.util.express.trash.exception.CalculateException;
import com.loposition.util.express.trash.exception.DictServiceNotFoundException;
import com.loposition.util.express.trash.exception.IncludeCannotUseException;
import com.loposition.util.express.trash.object.TrashBoolean;
import com.loposition.util.express.trash.object.TrashDict;
import com.loposition.util.express.trash.object.TrashDouble;
import com.loposition.util.express.trash.object.TrashList;
import com.loposition.util.express.trash.object.TrashLong;
import com.loposition.util.express.trash.object.TrashObject;
import com.loposition.util.express.trash.object.TrashString;
import com.loposition.util.express.trash.plugin.DictService;
import com.loposition.util.express.trash.plugin.dto.DictResultDTO;
import com.loposition.util.express.trash.token.BaseTrashToken;
import com.loposition.util.express.trash.token.DoubleTrashToken;
import com.loposition.util.express.trash.token.KeyWordTrashToken;
import com.loposition.util.express.trash.token.KeywordTrashType;
import com.loposition.util.express.trash.token.LongTrashToken;
import com.loposition.util.express.trash.token.OperatorTrashToken;
import com.loposition.util.express.trash.token.OperatorTrashType;
import com.loposition.util.express.trash.token.StringTrashToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @author huahua
 * @date create at 上午7:58 2018/4/6
 */
public class TrashCalculator {


  private Stack<TrashObject> tokens;


  private Iterator<BaseTrashToken> tokenIterator;

  private Boolean notFlag;

  private BaseTrashToken nowToken;

  private List<String> hitWords = new ArrayList<>(4);

  private List<String> dict = new ArrayList<>(4);


  public TrashCalculator(List<BaseTrashToken> tokens) {
    this.tokenIterator = tokens.iterator();
    this.tokens = new Stack<TrashObject>();
  }

  public Boolean calculator(Stack<TrashObject> tokens) {

    while (tokenIterator.hasNext()) {
      BaseTrashToken token = nextToken();
      if (token == null) {
        continue;
      }
      //push string
      if (token instanceof StringTrashToken) {
        StringTrashToken stringTrashToken = (StringTrashToken) token;
        if (stringTrashToken.getContent().startsWith("#")) {
          //外置词库
          tokens.push(new TrashList(stringTrashToken.getContent()));
        } else if (stringTrashToken.getContent().startsWith("$")) {
          //字典操作
          tokens.push(new TrashDict(
              stringTrashToken.getContent().substring(1)
          ));
        } else {
          //普通文字
          tokens.push(new TrashString(stringTrashToken.getContent()));
        }

      }
      //push long
      if (token instanceof LongTrashToken) {
        tokens.push(new TrashLong(
            ((LongTrashToken) token).getContent()));
      }
      if (token instanceof DoubleTrashToken) {
        tokens.push(new TrashDouble(
            ((DoubleTrashToken) token).getContent()));
      }

      if (token instanceof OperatorTrashToken) {
        //push list
        OperatorTrashToken operatorTrashToken = (OperatorTrashToken) token;
        //array
        if (operatorTrashToken.isOperate(OperatorTrashType.ARRAY_LEFT)) {
          pushList();
        }
        //()
        else if (operatorTrashToken.isOperate(OperatorTrashType.RIGHT)) {
          return null;
        } else if (operatorTrashToken.isOperate(OperatorTrashType.LEFT)) {
          tokens.push(new TrashBoolean(bracket()));
          token = nowToken;
        }
        //math operate
        mathOperate(tokens, operatorTrashToken);
      }

      //push keyword
      if (token instanceof KeyWordTrashToken) {
        KeyWordTrashToken keyWordTrashToken = (KeyWordTrashToken) token;
        //include in
        if (keyWordTrashToken.is(KeywordTrashType.INCLUDED)) {
          checkIncludeIn(tokens);
          BaseTrashToken expectToken = nextToken();
          if (expectToken instanceof StringTrashToken) {
            includeInString(tokens, (StringTrashToken) expectToken);
          } else if (expectToken instanceof LongTrashToken){
            includeInLong(tokens,(LongTrashToken)expectToken);
          }
        }
        // in
        else if (keyWordTrashToken.is(KeywordTrashType.IN)) {
          BaseTrashToken expectToken = nextToken();
          inString(tokens, (StringTrashToken) expectToken);
        }
        // or
        else if (keyWordTrashToken.is(KeywordTrashType.OR)) {
          //之前有真的直接返回
          if (orOperate(tokens)) {
            return Boolean.TRUE;
          }
        }
        //and
        else if (keyWordTrashToken.is(KeywordTrashType.AND)) {
          //之前有 false 直接返回 不然就把之前的 pop
          if (andOperate(tokens)) {
            return Boolean.FALSE;
          }
        } else if (keyWordTrashToken.is(KeywordTrashType.NOT)) {
          notFlag = true;
        }
        //contain
        else if (keyWordTrashToken.is(KeywordTrashType.CONTAIN)) {
          BaseTrashToken expectToken = nextToken();
          containDict(tokens, (StringTrashToken) expectToken);
        }
      }
    }
    return null;
  }



  private boolean andOperate(
      Stack<TrashObject> tokens) {
    TrashObject peek = tokens.peek();
    if (peek instanceof TrashBoolean) {
      TrashBoolean trashBoolean = (TrashBoolean) tokens.pop();
      return !((Boolean) trashBoolean.getContent());
    }
    return true;
  }

  /**
   * 看之前是否有为真的返回 直接出栈
   * ps or之前是 true 的话就是 true ,or之前为 false 的话可以直接忽略
   *
   * @return 是否需要执行下注
   */
  private boolean orOperate(
      Stack<TrashObject> tokens) {
    TrashObject peek = tokens.peek();
    if (peek instanceof TrashBoolean) {
      TrashBoolean trashBoolean = (TrashBoolean) tokens.pop();
      return ((Boolean) trashBoolean.getContent());
    }
    return false;
  }

  public Boolean calculator() {
    Boolean calculator = calculator(tokens);
    if (calculator != null) {
      return calculator;
    } else {
      return calculatorForBoolean(tokens);
    }
  }

  /**
   * bracket
   * 括号不支持
   */
  private Boolean bracket() {
    Stack<TrashObject> tempTokens = new Stack<>();
    Boolean calculator = calculator(tempTokens);
    //遍历到括号出的结果
    if (calculator == null) {
      return calculatorForBoolean(tempTokens);
    } else {
      //没遍历括号出的结果
      while (tokenIterator.hasNext()) {
        BaseTrashToken baseTrashToken = nextToken();
        if (baseTrashToken instanceof OperatorTrashToken) {
          //让表达式便利出括号
          if (((OperatorTrashToken) baseTrashToken).isOperate(OperatorTrashType.RIGHT)) {
            break;
          }
        }
      }
      return calculator;
    }

  }

  private Boolean calculatorForBoolean(Stack<TrashObject> tokens) {
    if (tokens.peek() instanceof TrashBoolean) {
      return (Boolean) tokens.peek().getContent();
    } else {
      throw new CalculateException();
    }
  }

  private void mathOperate(Stack<TrashObject> tokens, OperatorTrashToken operatorTrashToken) {
    Boolean ret = null;
    //>
    if (operatorTrashToken.isOperate(OperatorTrashType.GT)) {
      TrashObject trashObject = buildBasicTrashObjectByToken(tokenIterator.next());
      ret = tokens.pop().greaterThan(trashObject);
    }
    //>=
    else if (operatorTrashToken.isOperate(OperatorTrashType.GTE)) {
      TrashObject trashObject = buildBasicTrashObjectByToken(tokenIterator.next());
      ret = !tokens.pop().lessThan(trashObject);
    }
    //<
    else if (operatorTrashToken.isOperate(OperatorTrashType.LT)) {
      TrashObject trashObject = buildBasicTrashObjectByToken(tokenIterator.next());
      ret = tokens.pop().lessThan(trashObject);
    }
    //<=
    else if (operatorTrashToken.isOperate(OperatorTrashType.LTE)) {
      TrashObject trashObject = buildBasicTrashObjectByToken(tokenIterator.next());
      ret = !tokens.pop().greaterThan(trashObject);
    }
    //==
    else if (operatorTrashToken.isOperate(OperatorTrashType.EQUAL)) {
      TrashObject trashObject = buildBasicTrashObjectByToken(tokenIterator.next());
      ret = tokens.pop().equal(trashObject);
    }
    //!=
    else if (operatorTrashToken.isOperate(OperatorTrashType.UNEQUAL)) {
      TrashObject trashObject = buildBasicTrashObjectByToken(tokenIterator.next());
      ret = !tokens.pop().equal(trashObject);
    }
    if (ret != null) {
      tokens.push(new TrashBoolean(ret));
    }
  }

  private void inString(Stack<TrashObject> tokens,
      StringTrashToken expectToken) {
    TrashObject peek = tokens.peek();
    if (peek instanceof TrashList) {
      includeInString(tokens, expectToken);
    } else {
      tokens.pop();
      String word = String.valueOf(peek.getContent());
      Boolean hitFlag = expectToken.getContent().contains(word);
      if (hitFlag) {
        hitWords.add(word);
      }
      tokens.push(new TrashBoolean(hitFlag));
    }
  }


  private void containDict(
      Stack<TrashObject> tokens,
      StringTrashToken expectToken) {
    DictService defaultService = DictServiceHolder.getDefaultService();
    if (defaultService == null) {
      throw new DictServiceNotFoundException();
    }
    TrashObject peek = this.tokens.peek();
    if (peek instanceof TrashDict) {
      TrashDict pop = (TrashDict) this.tokens.pop();
      Boolean flag = Boolean.FALSE;
      DictResultDTO dictResultDTO = defaultService
          .textBelongsDict(pop.getContent(), expectToken.getContent());
      if (dictResultDTO != null && dictResultDTO.getFlag() != null){
        flag = dictResultDTO.getFlag();
        hitWords.addAll(dictResultDTO.getWords());
      }
      tokens.push(new TrashBoolean(flag));
      if (flag) {
        dict.add(pop.getContent());
      }
    }

  }

  /**
   * 压栈数组
   */
  private void pushList() {
    ArrayList<TrashObject> trashObjects = new ArrayList<TrashObject>();
    while (tokenIterator.hasNext()) {
      BaseTrashToken token = tokenIterator.next();
      if (token instanceof OperatorTrashToken) {
        OperatorTrashToken operatorTrashToken = (OperatorTrashToken) token;
        operatorTrashToken.isOperate(OperatorTrashType.ARRAY_RIGHT);
        tokens.push(new TrashList<TrashObject>(trashObjects));
        return;
      } else {
        tryAdd(trashObjects, token);
      }
    }

    throw new ArrayParseException();

  }

  private boolean checkIncludeIn(
      Stack<TrashObject> tokens) {
    TrashObject peek = tokens.peek();
    if (!(peek instanceof TrashList)) {
      throw new IncludeCannotUseException(
          "the object before include must be list");
    }
    BaseTrashToken next = tokenIterator.next();
    if (!(next instanceof KeyWordTrashToken)) {
      throw new IncludeCannotUseException(
          "the token after include must be 'in'");
    }
    if (!((KeyWordTrashToken) next).is(KeywordTrashType.IN)) {
      throw new IncludeCannotUseException(
          "the token after include must be 'in'");
    }
    return true;
  }


  /**
   * include in String
   */
  private void includeInString(
      Stack<TrashObject> tokens,
      StringTrashToken stringTrashToken) {
    TrashList<TrashObject> pop = (TrashList<TrashObject>) tokens.pop();
    List<TrashObject> trashObjects = pop.getContent();
    boolean flag = false;
    for (TrashObject trashObject : trashObjects) {
      String word = String.valueOf(trashObject.getContent());
      if (stringTrashToken.getContent().contains(word)) {
        flag = true;
        hitWords.add(word);
        break;
      }
    }
    this.tokens.push(new TrashBoolean(flag));

  }

  private void includeInLong(Stack<TrashObject> tokens, LongTrashToken expectToken) {
    TrashList<TrashObject> pop = (TrashList<TrashObject>) tokens.pop();
    List<TrashObject> trashObjects = pop.getContent();
    boolean flag = false;
    for (TrashObject trashObject : trashObjects) {
      Long num = Long.valueOf(String.valueOf(trashObject.getContent()));
      if (Objects.equals(expectToken.getContent(),num)) {
        flag = true;
        break;
      }
    }
    this.tokens.push(new TrashBoolean(flag));
  }


  private static void tryAdd(List<TrashObject> objects, BaseTrashToken baseTrashToken) {
    if (canAdd(objects, baseTrashToken)) {
      if (baseTrashToken instanceof StringTrashToken) {
        objects.add(new TrashString(((StringTrashToken) baseTrashToken).getContent()));
      }
      if (baseTrashToken instanceof LongTrashToken) {
        objects.add(new TrashLong(
            ((LongTrashToken) baseTrashToken).getContent()
        ));
      }
    } else {
      throw new ArrayParseException();
    }
  }


  private static boolean canAdd(List<TrashObject> objects, BaseTrashToken baseTrashToken) {
    if (objects.size() == 0) {
      return Boolean.TRUE;
    }
    TrashObject trashObject = objects.get(objects.size() - 1);
    if (baseTrashToken instanceof StringTrashToken
        && trashObject instanceof TrashString) {
      return Boolean.TRUE;
    }
    if (baseTrashToken instanceof LongTrashToken
        && trashObject instanceof TrashLong) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  private static TrashObject buildBasicTrashObjectByToken(BaseTrashToken baseTrashToken) {
    if (baseTrashToken instanceof LongTrashToken) {
      return new TrashLong(((LongTrashToken) baseTrashToken).getContent());
    } else if (baseTrashToken instanceof StringTrashToken) {
      return new TrashString(((StringTrashToken) baseTrashToken).getContent());
    } else if (baseTrashToken instanceof DoubleTrashToken) {
      return new TrashDouble(((DoubleTrashToken) baseTrashToken).getContent());
    }
    return null;
  }


  BaseTrashToken nextToken() {
    if (tokenIterator.hasNext()) {
      nowToken = tokenIterator.next();
    } else {
      nowToken = null;
    }
    return nowToken;
  }

  public List<String> getHitWords() {
    return hitWords;
  }

  public List<String> getDict() {
    return dict;
  }
}
