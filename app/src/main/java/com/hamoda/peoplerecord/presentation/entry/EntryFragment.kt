package com.hamoda.peoplerecord.presentation.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.NavDeepLinkRequest
import com.hamoda.peoplerecord.R

class EntryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.entry_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnXml).setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://com.peoplerecord/userfragment/xml".toUri())
                .build()
            findNavController().navigate(request)
        }
        view.findViewById<Button>(R.id.btnCompose).setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://com.peoplerecord/userfragment/compose".toUri())
                .build()
            findNavController().navigate(request)
        }
    }
}
