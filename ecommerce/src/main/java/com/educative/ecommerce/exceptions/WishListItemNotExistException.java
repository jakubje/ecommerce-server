package com.educative.ecommerce.exceptions;

public class WishListItemNotExistException extends Exception {
    public WishListItemNotExistException(String msg){
        super(msg);
    }
}
