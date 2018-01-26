package com.xia.emptyrecyclerview

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DemoActivity : AppCompatActivity() {

    // 空标识与Adapter
    private var isEmpty = true
    private var mAdapter: MyAdapter? = null

    /**
     * 生命周期：onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_demo)

        // 设置RecyclerView
        mAdapter = MyAdapter()
        val mRecyclerView = findViewById<RecyclerView>(R.id.mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        // 设置监听
        findViewById<View>(R.id.mValueOn).setOnClickListener {
            isEmpty = true
            mAdapter?.notifyDataSetChanged()
        }
        findViewById<View>(R.id.mValueOff).setOnClickListener {
            isEmpty = false
            mAdapter?.notifyDataSetChanged()
        }
    }

    /**
     * 列表Adapter
     */
    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

        /**
         * 构建ViewHolder
         */
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
            return MyViewHolder.create(this@DemoActivity, parent)
        }

        /**
         * 列表个数
         */
        override fun getItemCount(): Int {
            return if (isEmpty) 1 else 0
        }

        /**
         * 绑定ViewHolder
         */
        override fun onBindViewHolder(holder: MyViewHolder?, position: Int) { }
    }

    /**
     * 列表ViewHolder
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {

            /**
             * 构建ViewHolder
             */
            fun create(context: Context, parent: ViewGroup?): MyViewHolder {
                val view = LayoutInflater.from(context).inflate(R.layout.view_demo_adapter, parent, false)
                return MyViewHolder(view)
            }
        }
    }
}
