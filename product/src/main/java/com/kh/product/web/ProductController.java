package com.kh.product.web;

import com.kh.product.dao.Product;
import com.kh.product.svc.ProductSVC;
import com.kh.product.web.form.InquiryForm;
import com.kh.product.web.form.SaveForm;
import com.kh.product.web.form.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductSVC productSVC; //svc로 dao에 접근하기위해

  //save form
  @GetMapping("/add")
  public String saveForm(Model model) {
    SaveForm saveForm = new SaveForm();
    model.addAttribute("saveForm", saveForm);
    return "product/saveForm";
  }

  //save function
  @PostMapping("/add")
  public String save(@Valid @ModelAttribute SaveForm saveForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes
  ) {
    log.info("saveFrom={}", saveForm);

    //1)어노테이션 기반 데이터 검증
    //필드 오류
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}",bindingResult);
      return "product/saveForm";
    }
    //글로벌 오류
    if (saveForm.getQuantity() * saveForm.getPrice() > 100_000_000L) {
      bindingResult.reject("totalPrice",new String[]{"1000"},"");
    }

    if (saveForm.getQuantity() < 1 && saveForm.getQuantity() < 1) {
      bindingResult.reject("quantity", new String[]{"1","2"},"");
    }

    if (bindingResult.hasErrors()) {
      return "product/saveForm";
    }


    //2)save
    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice(saveForm.getPrice());

    Long saveProductId = productSVC.save(product);
    redirectAttributes.addAttribute("pid", saveProductId);

    return "redirect:/products/{pid}/inquiry";
  }


  //inquiry form
  @GetMapping("/{pid}/inquiry")
  public String findId(@PathVariable("pid") Long pid, Model model) {
    Optional<Product> foundProduct = productSVC.findId(pid);
    Product product = foundProduct.orElseThrow();

    InquiryForm inquiryForm = new InquiryForm();
    inquiryForm.setPid(product.getPid());
    inquiryForm.setPname(product.getPname());
    inquiryForm.setQuantity(product.getQuantity());
    inquiryForm.setPrice(product.getPrice());

    model.addAttribute("inquiryForm", inquiryForm);

    return "product/inquiryForm";
  }


  //update form
  @GetMapping("/{pid}/update")
  public String updateForm(@PathVariable("pid") Long pid, Model model) {
    Optional<Product> foundProduct = productSVC.findId(pid);
    Product product = foundProduct.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setPid(product.getPid());
    updateForm.setPname(product.getPname());
    updateForm.setQuantity(product.getQuantity());
    updateForm.setPrice(product.getPrice());

    model.addAttribute("updateForm", updateForm);
    return "product/updateForm";
  }


  //update function
  @PostMapping("/{pid}/update")
  public String update(
      @PathVariable("pid") Long pid,
      @Valid @ModelAttribute UpdateForm updateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {

    //1)어노테이션 데이터 검증
    //필드 오류
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}",bindingResult);
      return "product/updateForm";
    }

    //2)update ->  Product 타입에 리턴하여 수정된 데이터 덮어쓰기
    Product product = new Product();
    product.setPid(pid);
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());

    productSVC.update(pid, product);
    redirectAttributes.addAttribute("pid", pid);

    return "redirect:/products/{pid}/inquiry";
  }


  //delete function
  @GetMapping("/{pid}/delete")
  public String deleteId(@PathVariable("pid") Long pid) {

    productSVC.delete(pid);

    return "redirect:/products";
  }


  //list form
  @GetMapping
  public String foundAll(Model model) {
    List<Product> products = productSVC.findAll();
    model.addAttribute("products", products);

    return "product/listForm";
  }
}
