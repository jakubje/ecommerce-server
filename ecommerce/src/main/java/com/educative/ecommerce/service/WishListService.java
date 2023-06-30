package com.educative.ecommerce.service;

import com.educative.ecommerce.exceptions.WishListItemAlreadyExistsException;
import com.educative.ecommerce.exceptions.WishListItemNotExistException;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.model.WishList;
import com.educative.ecommerce.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    public void createWishlist(WishList wishList) throws WishListItemAlreadyExistsException {

        List<WishList> wishLists = readWishList(wishList.getUser());

        if(containsId(wishLists, wishList.getProduct().getId())){
            throw new WishListItemAlreadyExistsException("item already in wishlist");
        }

        wishListRepository.save(wishList);
    }

    public boolean containsId(final List<WishList> list, final int id){
        return list.stream().filter(o -> o.getProduct().getId().equals(id)).findFirst().isPresent();
    }

//    public int getById(final List<WishList> list, final int id){
//        return list.stream().filter(o -> o.getProduct().getId().equals(id));
//    }

    public List<WishList> readWishList(User user) {
        return wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public void deleteWishListItem(int wishListItemId, User user) throws WishListItemNotExistException {

//        Temp solution
//        List<WishList> wishLists = readWishList(user);
//        WishList optionalWishList1 = getById(wishLists, wishListItemId);
//        System.out.println(optionalWishList1);



//        Wish lists have same product ID but different user IDs, a single user can have many products
//        Find by userId - find by product id
//        Delete by productId by User id

        System.out.println(user.getId());
//        Optional<WishList> optionalWishList = wishListRepository.findAllByUserIdByProductId(user.getId(), wishListItemId);
//        System.out.println(optionalWishList);

        Optional<WishList> optionalWishList = wishListRepository.findByProductId(wishListItemId);

        if(!optionalWishList.isPresent()){
            throw new WishListItemNotExistException("wishListItemId not valid");
        }
        WishList wishList = optionalWishList.get();

        if(wishList.getUser() != user){
            throw new WishListItemNotExistException("wishlist item does not belong to user");
        }

        wishListRepository.deleteById(wishList.getId());
    }
}
