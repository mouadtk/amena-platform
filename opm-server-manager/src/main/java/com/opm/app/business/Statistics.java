/**
 * 
 */
package com.opm.app.business;

import java.util.Date;

/**
 * @author Mouad-tk
 *
 * 9 janv. 2017
 */
public interface Statistics {

	public int 	  getTotalSent(long idServer, Date from, Date to);
	public double getTotalEarning(long idServer, Date from, Date to);
	public int getTotalClick(long idServer, Date from, Date to);

}