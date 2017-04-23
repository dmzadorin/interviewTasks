/**
 * 
 */
package ru.dmzadorin.interview.tasks.extLib;

import ru.dmzadorin.interview.tasks.extLib.evaluator.ExtLib;

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
