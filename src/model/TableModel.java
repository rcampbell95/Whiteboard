package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sun.javafx.geom.Rectangle;
import view.DShape;

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
		modelArray.add(0, model);
		model.addListener(this);
		fireTableDataChanged();

	}

	public void removeModel(DShapeModel model) {
        model.removeListener(this);
        modelArray.remove(model);
        fireTableDataChanged();

    }

	public void clearTable()
	{
		if(!(modelArray.isEmpty()))
		{
			modelArray.clear();
			fireTableDataChanged();
		}
	}

	public int getRowForModel(DShapeModel model) {
	    return modelArray.indexOf(model);
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
		
		
		switch (arg1) {
        case 0:
            return modelArray.get(arg0).getBounds().getX();
        case 1:
            return modelArray.get(arg0).getBounds().getY();
        case 2:
            return modelArray.get(arg0).getBounds().getWidth();
        case 3:
            return modelArray.get(arg0).getBounds().getHeight();
        case 4:
            return modelArray.get(arg0);
        default:
            return null;}

		
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
