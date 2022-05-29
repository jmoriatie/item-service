package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("saveStatus", true);
        // ㄴ 구분하기 위한 : saveStatus true 면 저장된걸로 보고 '저장되었습니다' 등 메세지 출력

        return "redirect:/basic/items/{itemId}";
        // ㄴ redirectAttributes 에 있는 itemId 가 치환이 됨
        // ㄴ 나머지는 쿼리 파라미터로 넘어감 ?saveStatus=true
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item, RedirectAttributes redirectAttributes){
        itemRepository.update(itemId, item);
        redirectAttributes.addAttribute("updateStatus", true);
        return "redirect:/basic/items/{itemId}"; // redirect 시에는 경로 작성
    }

    @GetMapping("/delete")
    public String deleteForm(){
        return "basic/delete";
    }

    @GetMapping("{itemId}/delete")
    public String delete(@PathVariable Long itemId, RedirectAttributes redirectAttributes){
        itemRepository.delete(itemId);

        redirectAttributes.addAttribute("deleteId", itemId);
        redirectAttributes.addAttribute("deleteStatus", true);

        return "redirect:/basic/items/delete";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 50000, 50));
    }
}
