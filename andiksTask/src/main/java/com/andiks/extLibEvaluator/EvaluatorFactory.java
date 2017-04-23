/**
 * 
 */
package com.andiks.extLibEvaluator;

import com.andiks.extLibEvaluator.evaluator.ExtLib;

/**
 * Evaluator factory is used to create different versions of ExtLibEvaluator
 * 
 * @author Dmitry Zadorin
 *
 */
public interface EvaluatorFactory<E extends ExtLibEvaluator> {

	/**
	 * Creates new Instance of ExtLibEvaluator
	 * 
	 * @return ExtLibEvaluator
	 */
	E create(ExtLib extLib);
}
