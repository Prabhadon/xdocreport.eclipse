/*******************************************************************************
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com>, Pascal Leclercq <pascal.leclercq@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR - initial API and implementation
 *     Pascal Leclercq - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.pagination;

import org.eclipse.swt.SWT;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated when page selection, sort changed, total changed
 * occurs in a page controller {@link PaginationController}.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a page controller using the <code>addPageChangedListener</code>
 * method and removed using the <code>removePageChangedListener</code> method.
 * When page selection, sort changed, total changed occurs in a page controller
 * the appropriate method will be invoked.
 * </p>
 * 
 * @see PageChangedAdapter
 */
public interface PageChangedListener {

	
	/**
	 * Sent when page changed in the page controller
	 * {@link PaginationController}.
	 * 
	 * @param oldPageIndex
	 *            old page index.
	 * @param newPageIndex
	 *            new page index.
	 * @param controller
	 *            the page controller which have sent this event.
	 */
	public void pageIndexChanged(int oldPageIndex, int newPageIndex,
			PaginationController controller);

	/**
	 * Sent when total elements changed in the page controller
	 * {@link PaginationController}.
	 * 
	 * @param oldTotalElements
	 *            old total elements.
	 * @param newTotalElements
	 *            new total elements.
	 * @param controller
	 *            the page controller which have sent this event.
	 */
	public void totalElementsChanged(long oldTotalElements,
			long newTotalElements, PaginationController controller);

	/**
	 * Sent when sort changed in the page controller
	 * {@link PaginationController}.
	 * 
	 * @param oldPopertyName
	 *            old property name.
	 * @param propertyName
	 *            new property name.
	 * @param oldSortDirection
	 *            old sort direction : {@link SWT.UP}, {@link SWT.DOWN}.
	 * @param sortDirection
	 *            new sort direction : {@link SWT.UP}, {@link SWT.DOWN}.
	 * @param paginationController
	 */
	public void sortChanged(String oldPopertyName, String propertyName,
			int oldSortDirection, int sortDirection,
			PaginationController paginationController);

	/**
	 * Sent when page size changed in the page controller
	 * {@link PaginationController}.
	 * 
	 * @param oldPageSize
	 *            old page size.
	 * @param newPageSize
	 *            new page size.
	 * @param controller
	 *            the page controller which have sent this event.
	 */
	public void pageSizeChanged(int oldPageSize, int newPageSize,
			PaginationController paginationController);

}