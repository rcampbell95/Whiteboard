package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel implements ModelListener{

	ArrayList<DShapeModel> modelArray;
	
	public TableModel()
	{
		super();
		 modelArray = new  ArrayList<DShapeModel>();
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
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}
	

}
