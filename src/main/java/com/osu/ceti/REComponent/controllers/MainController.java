package com.osu.ceti.REComponent.controllers;

import java.io.IOException;
import java.util.ArrayList;

import opennlp.tools.util.InvalidFormatException;

import com.osu.ceti.REComponent.helpers.StringHelper;

public class MainController {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		String s = "This is a pleasant day"; 
		ArrayList<String> tokens = StringHelper.tokenize(s);
		for(String s2 : tokens) {
			System.out.println(s2);
		}
	}
}
