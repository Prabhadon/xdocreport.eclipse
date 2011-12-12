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
package org.eclipse.nebula.widgets.pagination.example;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.nebula.widgets.pagination.PageChangedAdapter;
import org.eclipse.nebula.widgets.pagination.PageChangedListener;
import org.eclipse.nebula.widgets.pagination.PaginationController;
import org.eclipse.nebula.widgets.pagination.springdata.PageableController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.collections.PageListHelper;
import org.springframework.data.domain.collections.PageLoader;

/**
 * Basic Picture control example.
 * 
 */
public class SortPageableTableExample3 {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);

		final List<String> items = createList();

		int pageSize = 10;

		final Table table = new Table(shell, SWT.BORDER | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		//
		// PageableTable pageableTable = new PageableTable(shell, SWT.BORDER,
		// pageSize, ResultAndPageLinksBannerWidgetFactory.getFactory(),
		// null);
		// pageableTable.setLayoutData(new GridData(GridData.FILL_BOTH));

		// 2) Initialize the table viewer
		final TableViewer viewer = new TableViewer(table);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new LabelProvider());

		final PageLoader pageLoader = PageListHelper.createPageLoader(items);
		final PageableController controller = new PageableController(pageSize);
		controller
				.addPageChangedListener(new PageChangedAdapter() {

					
					public void pageIndexChanged(int oldPageNumber,
							int newPageNumber, PaginationController controller) {

						Page<?> page = pageLoader
								.loadPage((PageableController) controller);
						controller.setTotalElements(page.getTotalElements());
						viewer.add(page.getContent().toArray());
						int count = viewer.getTable().getItemCount();
						if (count > 0) {
							TableItem item = viewer.getTable().getItem(count-1);
							item.setData("A", true);
						}
					}
				});

		viewer.getTable().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = (TableItem) e.item;
				int index = table.indexOf(item);

				if (item.getData("A") != null) {
					if (controller.hasNextPage()) {
						controller.setCurrentPage(controller.getCurrentPage() + 1);
					}
				}

				System.err.println(index);
				System.err.println(controller.getCurrentPage());
				System.err.println(controller.getTotalElements());

			}
		});

		// Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		createColumns(viewer);

		// 3) Set current page to 0 to refresh the table
		// pageableTable.setPageLoader(PageListHelper.createPageLoader(items));
		// pageableTable.setCurrentPage(0);
		controller.setCurrentPage(0);
		shell.setSize(350, 250);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static void createColumns(final TableViewer viewer) {

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(viewer, "Name", 150);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String p = (String) element;
				return p;
			}
		});
		// col.getColumn().addSelectionListener(
		// new SortTableColumnSelectionListener("name"));
		// // col.getColumn().addSelectionListener(new SelectionAdapter() {
		//
		// private boolean b = false;
		//
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// Order order = new Order(b ? Direction.ASC : Direction.DESC,
		// "name");
		// Sort sort = new Sort(order);
		// pageableTable.getController().setSort(sort);
		// pageableTable.refreshPage();
		// b = !b;
		// }
		//
		// });
	}

	private static List<String> createList() {
		List<String> names = new ArrayList<String>();
		for (int i = 1; i < 2012; i++) {
			names.add("Name " + i);
		}
		return names;
	}

	private static TableViewerColumn createTableViewerColumn(
			TableViewer viewer, String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

}
