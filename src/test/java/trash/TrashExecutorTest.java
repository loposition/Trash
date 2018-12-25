package trash;

import com.loposition.util.express.trash.TrashExecutor;
import com.loposition.util.express.trash.context.VarContextHolder;
import com.loposition.util.express.trash.dto.TrashResult;
import org.junit.Assert;
import org.junit.Test;
import trash.item.TestItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author huahua
 * @date create at 下午3:54 2018/4/5
 */
public class TrashExecutorTest {

  @Test
  public void testExecute() {

    HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
    stringObjectHashMap.put("test", 3244);
    TestItem hello = new TestItem("hello", 20L);
    stringObjectHashMap.put("item", hello);
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("hello");
    strings.add("hello1");
    strings.add("hello2");
    VarContextHolder.add("go", strings);
    String expression = "item:id < 10 or (test == 3244 and item:id == 20) ";

    TrashResult pass = TrashExecutor.pass(expression, stringObjectHashMap);
    Assert.assertTrue(pass.getFlag());
  }
}