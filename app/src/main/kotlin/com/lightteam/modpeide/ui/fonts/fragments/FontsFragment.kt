/*
 * Licensed to the Light Team Software (Light Team) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Light Team licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightteam.modpeide.ui.fonts.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.lightteam.modpeide.R
import com.lightteam.modpeide.data.feature.font.FontModel
import com.lightteam.modpeide.databinding.FragmentFontsBinding
import com.lightteam.modpeide.ui.base.adapters.OnItemClickListener
import com.lightteam.modpeide.ui.base.dialogs.DialogStore
import com.lightteam.modpeide.ui.base.fragments.BaseFragment
import com.lightteam.modpeide.ui.fonts.adapters.FontAdapter
import com.lightteam.modpeide.ui.fonts.viewmodel.FontsViewModel
import com.lightteam.modpeide.utils.extensions.isUltimate
import javax.inject.Inject

class FontsFragment : BaseFragment(), OnItemClickListener<FontModel> {

    @Inject
    lateinit var viewModel: FontsViewModel

    private lateinit var navController: NavController
    private lateinit var binding: FragmentFontsBinding
    private lateinit var adapter: FontAdapter

    override fun layoutId(): Int = R.layout.fragment_fonts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFontsBinding.bind(view)
        observeViewModel()

        navController = findNavController()
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(itemDecoration)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = FontAdapter(this).also {
            adapter = it
        }

        binding.actionAdd.setOnClickListener {
            if (requireContext().isUltimate()) {
                val destination = FontsFragmentDirections.toExternalFontFragment()
                navController.navigate(destination)
            } else {
                DialogStore.Builder(requireContext()).show()
            }
        }

        viewModel.fetchFonts()
    }

    override fun onClick(item: FontModel) {
        if (item.isPaid && !requireContext().isUltimate()) {
            DialogStore.Builder(requireContext()).show()
        } else {
            viewModel.selectFont(item)
        }
    }

    private fun observeViewModel() {
        viewModel.fontsEvent.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.selectionEvent.observe(viewLifecycleOwner, Observer {
            showToast(text = String.format(getString(R.string.message_selected), it))
        })
    }
}