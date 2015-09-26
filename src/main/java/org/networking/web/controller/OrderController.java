package org.networking.web.controller;

import org.networking.entity.Member;
import org.networking.entity.Product;
import org.networking.entity.SalesItem;
import org.networking.entity.SalesOrder;
import org.networking.service.AccountPointsService;
import org.networking.service.MemberService;
import org.networking.service.ProductService;
import org.networking.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/order")
public class OrderController extends BaseController<SalesOrder> {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountPointsService accountPointsService;

    @RequestMapping(method = RequestMethod.GET)
    public String getView() {
        return "admin-order";
    }

    @Override
    protected void preCreate(SalesOrder salesOrder) {
        Long sellerId = salesOrder.getSellerId();
        Member member = memberService.load(sellerId);
        salesOrder.setSeller(member);

        for(SalesItem item : salesOrder.getItems()) {
            item.setProduct(productService.load(item.getProductId()));
        }
    }

    @Override
    protected void postCreate(SalesOrder salesOrder) {
        //Create account points for the sales order
        accountPointsService.createForProduct(salesOrder);
    }
}