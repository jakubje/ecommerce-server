package com.educative.ecommerce.controllers;

import com.educative.ecommerce.common.ApiResponse;
import com.educative.ecommerce.dto.product.ProductDto;
import com.educative.ecommerce.exceptions.AuthenticationFailException;
import com.educative.ecommerce.exceptions.WishListItemAlreadyExistsException;
import com.educative.ecommerce.exceptions.WishListItemNotExistException;
import com.educative.ecommerce.model.Product;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.model.WishList;
import com.educative.ecommerce.repository.ProductRepository;
import com.educative.ecommerce.service.AuthenticationService;
import com.educative.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody ProductDto productDto, @RequestParam("token") String token) throws AuthenticationFailException, WishListItemAlreadyExistsException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        Product product = productRepository.getById(productDto.getId());

        WishList wishList = new WishList(user, product);
        wishListService.createWishlist(wishList);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) throws AuthenticationFailException{

        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        List<WishList> wishLists = wishListService.readWishList(user);

        List<ProductDto> products = new ArrayList<>();
        for (WishList wishList : wishLists ){
            products.add(new ProductDto(wishList.getProduct()));
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{wishListItemId}")
    public ResponseEntity<ApiResponse> deleteWishListItem(@PathVariable("wishListItemId") int wishListItemId, @RequestParam("token") String token) throws AuthenticationFailException, WishListItemNotExistException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        wishListService.deleteWishListItem(wishListItemId, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }
}
