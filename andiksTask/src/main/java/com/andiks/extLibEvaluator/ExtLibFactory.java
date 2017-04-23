package com.andiks.extLibEvaluator;

import com.andiks.extLibEvaluator.evaluator.ExtLib;

/**
 * ExtLibFactory is used to create different versions of ExtLib
 * @author Dmitry Zadorin
 *
 * @param <E>
 */
public interface ExtLibFactory<E extends ExtLib> {

	/**
	 * Creates new instance of ext lib
	 * 
	 * @return new instance of ext lib
	 */
	E createExtLib();
}
