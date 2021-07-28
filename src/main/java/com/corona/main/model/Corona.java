package com.corona.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Corona {

	private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDay;
}
