package ru.dmzadorin.interview.tasks.extLib.evaluator;

/**
 * 
 * @author zadorin
 *
 */
public class ExtLibImpl implements ExtLib {

	@Override
	public double eval(int a, int p) {
		return Math.pow(a, p);
	}
}
