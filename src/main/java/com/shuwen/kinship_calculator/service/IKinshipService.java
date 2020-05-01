package com.shuwen.kinship_calculator.service;

import com.shuwen.kinship_calculator.bean.Kinship;

import java.util.List;

public interface IKinshipService {

    List<String> callCalcultor(Kinship kinship);

}
