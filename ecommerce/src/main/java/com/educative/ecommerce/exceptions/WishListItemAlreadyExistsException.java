package com.educative.ecommerce.exceptions;

public class WishListItemAlreadyExistsException extends Throwable {
    public WishListItemAlreadyExistsException(String msg) {
        super(msg);
    }
}
