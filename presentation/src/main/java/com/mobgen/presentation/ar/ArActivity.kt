package com.mobgen.presentation.ar

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.mobgen.presentation.BaseViewModel
import com.mobgen.presentation.R
import kotlinx.android.synthetic.main.activity_ar.*
import kotlinx.android.synthetic.main.layout_pokemon.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class ArActivity : AppCompatActivity() {
    companion object {
        private const val ARG_ID = "id"
        private const val ARG_MODE = "mode"
        const val MODE_2D = "2d"
        fun newInstance(context: Context, id: Long, mode: String = "") =
            Intent(context, ArActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra(ARG_ID, id)
                putExtra(ARG_MODE, mode)
            }
    }

    private val viewModel by viewModel<ArViewModel>()
    private lateinit var pokemonRenderable: ModelRenderable
    private lateinit var viewRenderable: ViewRenderable
    private lateinit var arFragment: ArFragment
    private var mode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        viewModel.data.observe(this, Observer {
            it?.let { data ->
                when (data.status) {
                    BaseViewModel.Status.LOADING -> {
                        load.visibility = View.VISIBLE
                    }
                    BaseViewModel.Status.SUCCESS -> {
                        load.visibility = View.GONE
                        bindData(data.ar)
                    }
                    BaseViewModel.Status.ERROR -> {
                        Toast.makeText(this, getString(R.string.checkConnection), Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        initView()
        initListeners()
        mode = intent.getStringExtra(ARG_MODE)
        viewModel.getPokemonById(intent.getLongExtra(ARG_ID, -1))
    }

    private fun initView() {
        arFragment = arFragmentAux as ArFragment
        Glide.with(this).load(getDrawable(R.drawable.pokeball_gif)).into(load)
    }

    private fun initListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun activeArCore(model3d: Int, arBindView: ArViewModel.ArBindView) {
        if (mode != MODE_2D) {
            ModelRenderable.builder()
                .setSource(this, model3d)
                .build()
                .thenAccept { renderable -> this.pokemonRenderable = renderable }
                .exceptionally { _ ->
                    Toast.makeText(this, "Unable to load andy pokemonRenderable", Toast.LENGTH_LONG).show()
                    null
                }
        }

        ViewRenderable.builder()
            .setView(this, R.layout.layout_pokemon)
            .build()
            .thenAccept { renderable ->
                viewRenderable = renderable
                viewRenderable.isShadowCaster = false
                viewRenderable.view.pokeName.text = arBindView.name
                if (mode == MODE_2D) Glide.with(this).load(arBindView.image).into(viewRenderable.view.pokeImage).waitForLayout()

                viewRenderable.view.imageType01.setImageDrawable(getDrawable(arBindView.type.first().second))
                viewRenderable.view.textType01.text = arBindView.type.first().first


                if (arBindView.type.size > 1) {
                    viewRenderable.view.type02.visibility = View.VISIBLE
                    viewRenderable.view.imageType02.setImageDrawable(getDrawable(arBindView.type.last().second))
                    viewRenderable.view.textType02.text = arBindView.type.last().first
                }
            }
            .exceptionally { _ ->
                Toast.makeText(this, "Unable to load andy pokemonRenderable", Toast.LENGTH_LONG).show()
                null
            }

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, _: Plane, _: MotionEvent ->

            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            val transformableNodeView = TransformableNode(arFragment.transformationSystem)
            transformableNodeView.localPosition =
                Vector3(
                    transformableNodeView.localPosition.x,
                    transformableNodeView.localPosition.y,
                    transformableNodeView.localPosition.z + 0.8f
                )
            transformableNodeView.scaleController.maxScale = 1.21f
            transformableNodeView.scaleController.minScale = 1.2f
            transformableNodeView.localRotation = Quaternion.axisAngle(Vector3(1f, 0f, 0f), -90f)
            transformableNodeView.setParent(anchorNode)
            transformableNodeView.renderable = viewRenderable
            transformableNodeView.select()

            if (mode != MODE_2D) {
                val transformableNodePokemon = TransformableNode(arFragment.transformationSystem)
                transformableNodePokemon.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 180f)
                transformableNodePokemon.setParent(anchorNode)
                transformableNodePokemon.renderable = pokemonRenderable
                transformableNodePokemon.select()
            }
        }
    }

    private fun bindData(ar: ArViewModel.ArBindView?) {
        ar?.let {
            activeArCore(ar.design3d, it)
        }
    }
}
