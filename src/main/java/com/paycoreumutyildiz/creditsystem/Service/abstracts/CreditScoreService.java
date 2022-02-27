package com.paycoreumutyildiz.creditsystem.Service.abstracts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface CreditScoreService {
    //Credit Score List for random selection
    default Integer getRandomCreditScore(){
        List<Integer> creditScore = new ArrayList<>();

        creditScore.add(425);
        creditScore.add(500);
        creditScore.add(1000);

        Integer upperBound = 3;
        Random random = new Random();

        return creditScore.get(random.nextInt(upperBound));
    }
}
