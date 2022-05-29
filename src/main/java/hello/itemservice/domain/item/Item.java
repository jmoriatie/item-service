package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Data 는 핵심 도메인을 컨트롤 하기 위험함 dto 같은 부분만 사용 가능
@Getter @Setter
public class Item {

    public Long id;
    public String itemName;
    public Integer price;
    public Integer quantity;

    public Item(){}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
