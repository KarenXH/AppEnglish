package education.nhom11.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import education.nhom11.tuvungtienganh.R;

public class GridAdapter extends ArrayAdapter {

    private Activity context;
    private int resource;
    private List objects;

    public GridAdapter(Activity context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        final Button btnKyTu = row.findViewById(R.id.btnKyTu);
        final String kyTu = (String) this.objects.get(position);
        if(kyTu.equals(" "))
        {
            btnKyTu.setText(kyTu);
            btnKyTu.setVisibility(View.INVISIBLE);
        }
        else
        {
            btnKyTu.setText("?");
        }
        btnKyTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKyTu.setText(kyTu);
            }
        });

        return row;
    }
}
