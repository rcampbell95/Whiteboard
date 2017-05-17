package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel implements ModelListener{

	ArrayList<DShapeModel> modelArray;
	public static final String [] COLUMN_HEADERS = { "X", "Y", "Width", "Height"};	

	public TableModel()
	{
		super();		
		modelArray = new  ArrayList<DShapeModel>();		
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_HEADERS[column];
	}
	

	public void addModel(DShapeModel model)
	{
		this.modelArray.add(0, model);
		model.addTableListener(this);
		fireTableDataChanged();

	}

	public void removeModel(DShapeModel model)
	{
		if(this.modelArray.contains(model))
		{
			model.removeTableListener(this);
			this.modelArray.remove(model);
		}
		fireTableDataChanged();

	}

	public void clearTable()
	{
		if(!(modelArray.isEmpty()))
		{
			this.modelArray.clear();
			fireTableDataChanged();
		}
	}


	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {

		return modelArray.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {

		 Rectangle bounds = modelArray.get(arg0).getBounds();
	        switch (arg1) {
	        case 0:
	            return bounds.x;
	        case 1:
	            return bounds.y;
	        case 2:
	            return bounds.width;
	        case 3:
	            return bounds.height;
	        case 4:
	            return modelArray.get(arg0);
	        default:
	            return null;
	        }

		return null;
	}

	@Override
	public void modelChanged(DShapeModel model) {

		if(!modelArray.contains(model))
		{
			modelArray.add(0,model);
		}
		else{
			
		 int index = modelArray.indexOf(model);
	     fireTableRowsUpdated(index, index);
		}
	}

	public void moveToFront(DShapeModel model)
	{
		if(!modelArray.isEmpty() && modelArray.contains(model))
		{
			modelArray.remove(model);
			modelArray.add(0, model);
			
		}
	}

	public void moveToBack(DShapeModel model)
	{
		if(!modelArray.isEmpty() && modelArray.contains(model))
		{
			modelArray.remove(model);
			modelArray.add(model);
			
		}
	}
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

}
