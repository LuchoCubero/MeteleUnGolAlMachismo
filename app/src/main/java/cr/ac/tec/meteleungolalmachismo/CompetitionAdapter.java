package cr.ac.tec.meteleungolalmachismo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CompetitionAdapter extends BaseAdapter{
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private static final int MATCH_ITEM = 0;
    private static final int HEADER = 1;


    public CompetitionAdapter(Context context, ArrayList<Object> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof Match)
        {
            return MATCH_ITEM;
        }
        else
        {
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            switch (getItemViewType(i)){
                case MATCH_ITEM: {
                    view = inflater.inflate(R.layout.listview_content, null);
                    break;
                }
                case HEADER: {
                    view = inflater.inflate(R.layout.listview_header, null);
                    break;
                }
            }
        }

        switch (getItemViewType(i)){
            case MATCH_ITEM: {
                TextView team1 = (TextView) view.findViewById(R.id.textView_team1);
                TextView score1 = (TextView) view.findViewById(R.id.textView_score1);
                TextView team2 = (TextView) view.findViewById(R.id.textView_team2);
                TextView score2 = (TextView) view.findViewById(R.id.textView_score2);
                TextView time = (TextView) view.findViewById(R.id.textView_time);

                team1.setText(((Match) list.get(i)).getMatch_hometeam_name());
                team2.setText(((Match) list.get(i)).getMatch_awayteam_name());
                score1.setText(((Match) list.get(i)).getMatch_hometeam_score());
                score2.setText(((Match) list.get(i)).getMatch_awayteam_score());
                time.setText(((Match) list.get(i)).getMatch_status());
                break;
            }
            case HEADER: {

                TextView header = (TextView) view.findViewById(R.id.textView_header);
                header.setText(((Competition) list.get(i)).getLeague_name());
                break;
            }
        }
        return view;
    }
}
