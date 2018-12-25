package com.loposition.util.express.trash.lexer;

import com.loposition.util.express.trash.exception.StringParseException;
import com.loposition.util.express.trash.token.BaseTrashToken;
import com.loposition.util.express.trash.token.DoubleTrashToken;
import com.loposition.util.express.trash.token.KeyWordTrashToken;
import com.loposition.util.express.trash.token.KeywordTrashType;
import com.loposition.util.express.trash.token.LongTrashToken;
import com.loposition.util.express.trash.token.OperatorTrashToken;
import com.loposition.util.express.trash.token.OperatorTrashType;
import com.loposition.util.express.trash.token.StringTrashToken;
import com.loposition.util.express.trash.token.VariableTrashToken;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author huahua
 * @date create at 下午5:54 2018/4/4
 */
public class ExpressionLexer {

  private String expression;

  private CharacterIterator characterIterator;

  private Character peek;

  private static final Set<Character> VALID_NUM_CHAR;

  private static final Set<Character> VALID_OPT_CHAR;


  static {

    VALID_NUM_CHAR = new HashSet<Character>();
    for (int i = 0; i < 10; i++) {
      VALID_NUM_CHAR.add((char) ('0' + i));
    }
    VALID_NUM_CHAR.add('.');

    VALID_OPT_CHAR = new HashSet<Character>();
    VALID_OPT_CHAR.add('!');
    VALID_OPT_CHAR.add('=');
    VALID_OPT_CHAR.add('>');
    VALID_OPT_CHAR.add('<');
    VALID_OPT_CHAR.add('(');
    VALID_OPT_CHAR.add(')');
    VALID_OPT_CHAR.add('{');
    VALID_OPT_CHAR.add('}');

  }

  public ExpressionLexer(String expression) {
    this.expression = expression;
    characterIterator = new StringCharacterIterator(expression);
    peek = characterIterator.current();
  }

  public BaseTrashToken scan() {
    for (; ; nextChar()) {
      if (peek.equals(CharacterIterator.DONE)) {
        return null;
      }

      //blank operate
      if (peek.equals(' ') || peek.equals('\n') || peek.equals('\r')) {
        continue;
      }

      if (this.peek == '"' || this.peek == '\'') {
        char left = this.peek;
        int startIndex = this.characterIterator.getIndex();
        StringBuilder sb = new StringBuilder();
        while ((this.peek = this.characterIterator.next()) != left) {
          if (this.peek == CharacterIterator.DONE) {
            throw new StringParseException(startIndex);
          } else {
            sb.append(this.peek);
          }
        }
        this.nextChar();
        return new StringTrashToken(sb.toString());
      }

      //long or double
      if (VALID_NUM_CHAR.contains(peek)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(peek);
        while (
            (peek = this.characterIterator.next()) != CharacterIterator.DONE
                && VALID_NUM_CHAR.contains(peek)) {
          stringBuilder.append(peek);
        }
        if (stringBuilder.indexOf(".") > -1) {
          return new DoubleTrashToken(Double.parseDouble(stringBuilder.toString()));
        }
        return new LongTrashToken(Integer.parseInt(stringBuilder.toString()));
      }

      //operate
      if (VALID_OPT_CHAR.contains(peek)) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(peek);
        OperatorTrashType operate = OperatorTrashType.of(stringBuffer.toString());
        nextChar();
        stringBuffer.append(peek);
        OperatorTrashType tryOperate = OperatorTrashType.of(stringBuffer.toString());
        if (tryOperate == null) {
          return new OperatorTrashToken(operate);
        } else {
          nextChar();
          return new OperatorTrashToken(tryOperate);
        }

      }

      //keyword and variable
      if (Character.isJavaIdentifierStart(peek)) {
        StringBuilder sb = new StringBuilder();
        do {
          sb.append(this.peek);
          this.nextChar();
        } while (Character.isJavaIdentifierPart(this.peek) || this.peek == ':');
        KeywordTrashType keyword = KeywordTrashType.of(sb.toString());
        if (keyword != null) {
          return new KeyWordTrashToken(keyword);
        } else {
          return new VariableTrashToken(sb.toString());
        }
      }
    }
  }


  private Iterator<Character> buildCharacterIterator(String expression) {
    char[] chars = expression.toCharArray();
    ArrayList<Character> characters = new ArrayList<Character>(chars.length);
    for (char ch : chars) {
      characters.add(ch);
    }
    return characters.iterator();
  }


  public void nextChar() {
    this.peek = this.characterIterator.next();
  }

}
