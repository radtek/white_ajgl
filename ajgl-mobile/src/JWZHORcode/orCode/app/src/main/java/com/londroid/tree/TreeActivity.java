/**
 * 软件名称:android无限级树源码
 * 作者：shaolong
 * 网址:http://londroid.5d6d.com
 */

package com.londroid.tree;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.taiji.pubsec.orcode.addressorcode.R;

public class TreeActivity extends Activity implements OnItemClickListener{

	ListView code_list;
	private LinearLayout toolBar;
	TreeActivity oThis = this;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collection_maintree);

		toolBar = (LinearLayout) findViewById(R.id.toolBar);
		code_list = (ListView)findViewById(R.id.code_list);
		code_list.setOnItemClickListener(this);

		setToolBar(new String[]{ "选中结果","","","确认" },new int[]{0, 3});

		setPreson();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub



		// 这句话写在最后面
		((TreeAdapter)parent.getAdapter()).ExpandOrCollapse(position);
	}
	// Download by http://www.codefans.net
	// 设置节点,可以通过循环或递归方式添加节点
	private void setPreson(){
		// 创建根节点
		/*
        Node root = new Node("合肥市公安局","000000");
		root.setIcon(R.drawable.icon_department);//设置图标
		//root.setCheckBox(false);//设置节点前有无复选框
		
		
		// 创建1级子节点
		Node n1 = new Node("治安警察大队","1");
		n1.setParent(root);//设置父节点
		n1.setIcon(R.drawable.icon_department);
		
		Node n11 = new Node("李伟","13966664567");
		n11.setParent(n1);
		n11.setIcon(R.drawable.icon_police);
		Node n12 = new Node("张同刚","13966664567");
		n12.setParent(n1);
		n12.setIcon(R.drawable.icon_police);
		
		n1.add(n11);
		n1.add(n12);
		
		
		// 创建1级子节点
		Node n2 = new Node("刑事警察大队","2");
		n2.setParent(root);
		n2.setIcon(R.drawable.icon_department);
		Node n21 = new Node("曹梦华","13966664567");
		n21.setParent(n2);
		n21.setIcon(R.drawable.icon_police);
		Node n22 = new Node("文燕","13966664567");
		n22.setParent(n2);
		n22.setIcon(R.drawable.icon_police);
		Node n23 = new Node("赵文涛","13766604867");
		n23.setParent(n2);
		n23.setIcon(R.drawable.icon_police);
		n2.add(n21);
		n2.add(n22);
		n2.add(n23);
		
		
		// 创建1级子节点
		Node n3 = new Node("巡警防暴大队","3");
		n3.setParent(root);
		n3.setIcon(R.drawable.icon_department);
		Node n31 = new Node("崔逊田","15305510131");
		n31.setParent(n3);
		n31.setIcon(R.drawable.icon_police);
		Node n32 = new Node("测试用户","13855196982");
		n32.setParent(n3);
		n32.setIcon(R.drawable.icon_police);
		
		//创建2级子节点
		Node n33 = new Node("巡警第一中队","31");
		n33.setParent(n3);
		n33.setIcon(R.drawable.icon_department);
		
		Node n331 = new Node("张楠","15890875672");
		n331.setParent(n33);
		//n331.setIcon(R.drawable.icon_police);
		Node n332 = new Node("阮明东","15890875672");
		n332.setParent(n33);
		n332.setIcon(R.drawable.icon_police);
		Node n333 = new Node("司徒正雄","15890875672");
		n333.setParent(n33);
		//n333.setIcon(R.drawable.icon_police);
		n33.add(n331);
		n33.add(n332);
		n33.add(n333);
		
		n3.add(n31);
		n3.add(n32);
		n3.add(n33);
		
		
		
		root.add(n3);
		root.add(n1);
		root.add(n2);
		*/
		Node root0 = new Node("公安局","0");
		//root0.setIcon(R.drawable.icon_department);//设置图标

		Node root01 = new Node("北京公安局","0");
		root01.setParent(root0);//设置父节点
		//root01.setIcon(R.drawable.icon_department);//设置图标

		Node n01 = new Node("治安警察大队","1");
		n01.setParent(root01);//设置父节点
		//n01.setIcon(R.drawable.icon_department);

		Node n011 = new Node("李伟01","13666666");
		n011.setParent(n01);
		//n011.setIcon(R.drawable.icon_police);
		Node n012 = new Node("张同刚01","1377777");
		n012.setParent(n01);
		//n012.setIcon(R.drawable.icon_police);

		n01.add(n011);
		n01.add(n012);
		root01.add(n01);


		Node root = new Node("合肥市公安局","000000");
		root.setParent(root0);//设置父节点
		//root.setIcon(R.drawable.icon_department);//设置图标
		//root.setCheckBox(false);//设置节点前有无复选框


		// 创建1级子节点
		Node n1 = new Node("治安警察大队","1");
		n1.setParent(root);//设置父节点
		//n1.setIcon(R.drawable.icon_department);

		Node n11 = new Node("李伟","13966664567");
		n11.setParent(n1);
		//n11.setIcon(R.drawable.icon_police);
		Node n12 = new Node("张同刚","13966664567");
		n12.setParent(n1);
		//n12.setIcon(R.drawable.icon_police);

		n1.add(n11);
		n1.add(n12);


		// 创建1级子节点
		Node n2 = new Node("刑事警察大队","2");
		n2.setParent(root);
		//n2.setIcon(R.drawable.icon_department);
		Node n21 = new Node("曹梦华","13966664567");
		n21.setParent(n2);
		//n21.setIcon(R.drawable.icon_police);
		Node n22 = new Node("文燕","13966664567");
		n22.setParent(n2);
		//n22.setIcon(R.drawable.icon_police);
		Node n23 = new Node("赵文涛","13766604867");
		n23.setParent(n2);
		//n23.setIcon(R.drawable.icon_police);
		n2.add(n21);
		n2.add(n22);
		n2.add(n23);


		// 创建1级子节点
		Node n3 = new Node("巡警防暴大队","3");
		n3.setParent(root);
	//	n3.setIcon(R.drawable.icon_department);
		Node n31 = new Node("崔逊田","15305510131");
		n31.setParent(n3);
		//n31.setIcon(R.drawable.icon_police);
		Node n32 = new Node("测试用户","13855196982");
		n32.setParent(n3);
	//	n32.setIcon(R.drawable.icon_police);

		//创建2级子节点
		Node n33 = new Node("巡警第一中队","31");
		n33.setParent(n3);
	//	n33.setIcon(R.drawable.icon_department);

		Node n331 = new Node("张楠","15890875672");
		n331.setParent(n33);
		//n331.setIcon(R.drawable.icon_police);
		Node n332 = new Node("阮明东","15890875672");
		n332.setParent(n33);
		//n332.setIcon(R.drawable.icon_police);
		Node n333 = new Node("司徒正雄","15890875672");
		n333.setParent(n33);
		//n333.setIcon(R.drawable.icon_police);
		n33.add(n331);
		n33.add(n332);
		n33.add(n333);

		n3.add(n31);
		n3.add(n32);
		n3.add(n33);



		root.add(n3);
		root.add(n1);
		root.add(n2);

		root0.add(root);
		root0.add(root01);

		TreeAdapter ta = new TreeAdapter(oThis,root0);
		// 设置整个树是否显示复选框
		ta.setCheckBox(true);
		// 设置展开和折叠时图标
		ta.setExpandedCollapsedIcon(R.drawable.tree_ex, R.drawable.tree_ec);
		// 设置默认展开级别
		ta.setExpandLevel(2);
		code_list.setAdapter(ta);
	}


	// 设置底部工具栏
	private void setToolBar(String[] name_array,int[] pos_array){
		toolBar.removeAllViews();

		GridView toolbarGrid = new GridView(this);
		toolbarGrid.setNumColumns(4);// 设置每行列数
		toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
		ToolbarAdapter adapter = new ToolbarAdapter(this,name_array,null,pos_array);
		toolbarGrid.setAdapter(adapter);
		toolbarGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				switch (arg2) {
					case 0://显示选中结果
						List<Node> nodes = ((TreeAdapter)code_list.getAdapter()).getSeletedNodes();
						String msg = "";

						for(int i=0;i<nodes.size();i++){
							Node n = nodes.get(i);
							if(n.isLeaf()){
								if(!msg.equals(""))msg+=",";
								msg += n.getText()+"("+n.getValue()+")";
							}

						}
						if(msg.equals("")){
							Toast.makeText(oThis, "没有选中任何项", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(oThis, msg, Toast.LENGTH_SHORT).show();
						}

						break;
					case 3://返回
						oThis.finish();
						System.exit(0);
						break;
				}
			}
		});
		toolBar.addView(toolbarGrid);
	}
}