package com.shenzhen.kinship_calculator.service;

import com.shenzhen.kinship_calculator.bean.Kinship;

import java.util.List;

public interface IKinshipService {

    List<String> callCalcultor(Kinship kinship);

}
