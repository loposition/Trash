package trash.item;

import com.loposition.util.express.trash.annotation.TrashField;
import com.loposition.util.express.trash.annotation.TrashObject;

/**
 * @author huahua
 * @date create at 下午7:20 2018/4/5
 */
@TrashObject( "item")
public class TestItem {

  @TrashField("name")
  private String content;

  private Long id;

  @TrashField("item")
  private SubItem subItem;

  public TestItem(String content ,Long id) {
    this.content = content;
    this.id = id;
  }

  public TestItem(String content ,Long id,SubItem subItem) {
    this.content = content;
    this.id = id;
    this.subItem = subItem;
  }

  public String getContent() {
    return content;
  }

  public Long getId(){return id;}
}
