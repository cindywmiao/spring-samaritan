package com.coupang.samaritan.domain.resource;

/**
 * Created by wuzonghan on 16/12/15.
 */
@FunctionalInterface
public interface FindTargetable<AF, V, R> {

	/**
	 * Applies this function to the given argument.
	 */
	R apply(AF allFiles, V value);
}
