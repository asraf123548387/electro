package com.shopping.electroshopping.service.referalOfferService;

import com.shopping.electroshopping.model.referaloffer.ReferalOffer;
import com.shopping.electroshopping.repository.ReferralOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReferralOfferService {
    @Autowired
    private ReferralOfferRepository referralOfferRepository;
    public ReferalOffer createReferralOffer(Long referrerUserId, Long referredUserId) {
        ReferalOffer referralOffer = new ReferalOffer();
        referralOffer.setReferrerId(referrerUserId);
        referralOffer.setReferredCustomerId(referredUserId);
        referralOffer.setStatus("pending"); // You can use statuses like "pending," "validated," etc.
        return referralOfferRepository.save(referralOffer);
    }
}
