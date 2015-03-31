package moritzgoeckel.com.mg_android_btce_client;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import moritzgoeckel.com.mg_android_btce_client.Client.AsyncBtcApi;
import moritzgoeckel.com.mg_android_btce_client.Client.BTCE;
import moritzgoeckel.com.mg_android_btce_client.Client.GlobalData;
import moritzgoeckel.com.mg_android_btce_client.Data.HistoryListAdapter;
import moritzgoeckel.com.mg_android_btce_client.Data.OrdersListAdapter;

public class FragmentOrders extends ListFragment{
    OrdersListAdapter adapter;
    List<BTCE.OrderListOrder> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        redraw();

        GlobalData.API.addListener(new AsyncBtcApi.BitcoinDataListener() {
            @Override
            public void onAccountDataChanged(BTCE.Info info) {

            }

            @Override
            public void onHistoryDataChanged(BTCE.TradeHistory history) {
                redraw();
            }

            @Override
            public void onOpenOrdersDataChanged(BTCE.OrderList openOrders) {

            }

            @Override
            public void onPairDataChanged(String pair, BTCE.Ticker ticker) {

            }
        });

        return rootView;
    }

    private void redraw() {
        BTCE.OrderList orders = GlobalData.API.getOpenOrders();
        if(orders != null)
        {
            orderList = arrayToList(orders.info.orders);

            adapter = new OrdersListAdapter(getActivity(), orderList);
            setListAdapter(adapter);
        }
    }

    private List<BTCE.OrderListOrder> arrayToList(BTCE.OrderListOrder[] array){
        List<BTCE.OrderListOrder> tmpList = new ArrayList<>();

        for(int i = 0; i < array.length; i++){
            tmpList.add(array[i]);
        }

        return tmpList;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        BTCE.OrderListOrder item = this.orderList.get(position);
        Toast.makeText(getActivity(), item.order_details.rate + " Clicked!", Toast.LENGTH_SHORT).show();
    }
}
