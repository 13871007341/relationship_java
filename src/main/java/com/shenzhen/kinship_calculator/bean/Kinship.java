package com.shenzhen.kinship_calculator.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pengshihao
 * @since 2019-10-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kinship implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer gender;//0:男；1:女；
	private String  relationShip;
}
