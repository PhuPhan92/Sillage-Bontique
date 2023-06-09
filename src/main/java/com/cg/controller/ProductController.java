package com.cg.controller;

import com.cg.exception.UnauthorizedException;
import com.cg.model.dto.StaffInfoDTO;
import com.cg.service.product.IProductService;
import com.cg.service.staff.IStaffService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
        @Autowired
        private AppUtils appUtils;

        @Autowired
        private IProductService productService;

        @Autowired
        private IStaffService staffService;




        @GetMapping
        public String showListProductPage(Model model) {

            String username = appUtils.getPrincipalUsername();

            Optional<StaffInfoDTO> staffInfoDTO = staffService.getStaffInfoByUsername(username);

            if (!staffInfoDTO.isPresent()) {
                throw new UnauthorizedException("Staff is not exists");
            }

            String fullName = staffInfoDTO.get().getFullName();

//        List<Customer> customers = customerService.findAll();
//
            model.addAttribute("fullName", fullName);

            return "product/list";
        }
}
