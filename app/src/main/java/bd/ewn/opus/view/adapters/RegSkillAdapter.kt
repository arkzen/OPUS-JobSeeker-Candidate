package bd.ewn.opus.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import bd.ewn.opus.databinding.SkillSingleitemBinding
import bd.ewn.opus.service.model.SkillItem

class RegSkillAdapter(private val skills: List<String>) :
    RecyclerView.Adapter<RegSkillAdapter.RegSkillViewholder>() {

    private val selectedSkills = MutableList(skills.size) { false }
    private val selectedSkillsList: ArrayList<SkillItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegSkillViewholder {
        val binding =
            SkillSingleitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegSkillViewholder(binding)
    }





    override fun getItemCount(): Int {
        return skills.size
    }

    override fun onBindViewHolder(holder: RegSkillViewholder, position: Int) {
        holder.bind(position)
    }

    fun getSelectedSkillsList(): List<SkillItem> {
        return selectedSkillsList
    }


    inner class RegSkillViewholder(val binding: SkillSingleitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rbSkill.setOnClickListener {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    selectedSkills[adapterPosition] = !selectedSkills[adapterPosition]
                    binding.skillstars.visibility =
                        if (selectedSkills[adapterPosition]) View.VISIBLE else View.GONE

                    binding.rbSkill.isChecked = selectedSkills[adapterPosition]
                }
            }

            val context = itemView.context
            val starsLayout = binding.skillstars
            val star1 = binding.star1
            val star2 = binding.star2
            val star3 = binding.star3
            val star4 = binding.star4
            val star5 = binding.star5

            star1.setOnClickListener {
                updateStars(starsLayout, 1, context)
                updateSelectedSkills(adapterPosition, 1)
            }

            star2.setOnClickListener {
                updateStars(starsLayout, 2,context)
            }

            star3.setOnClickListener {
                updateStars(starsLayout, 3,context)
            }

            star4.setOnClickListener {
                updateStars(starsLayout, 4,context)
            }

            star5.setOnClickListener {
                updateStars(starsLayout, 5,context)
            }
        }

        fun bind(position: Int) {
            binding.rbSkill.text = skills[position]
            binding.skillstars.visibility =
                if (selectedSkills[position]) View.VISIBLE else View.GONE

            binding.rbSkill.isChecked = selectedSkills[position]
        }

        private fun updateStars(starsLayout: LinearLayout, selectedStars: Int, context: Context) {
            for (i in 1..5) {
                val grayStar = starsLayout.findViewById<ImageView>(
                    context.resources.getIdentifier(
                        "grystar$i",
                        "id",
                        context.packageName
                    )
                )
                val yellowStar = starsLayout.findViewById<ImageView>(
                    context.resources.getIdentifier(
                        "ylwstar$i",
                        "id",
                        context.packageName
                    )
                )

                if (i <= selectedStars) {
                    yellowStar.visibility = View.VISIBLE
                    grayStar.visibility = View.GONE
                } else {
                    yellowStar.visibility = View.GONE
                    grayStar.visibility = View.VISIBLE
                }
            }
        }



        private fun updateSelectedSkills(adapterPosition: Int, selectedStars: Int) {
            val skillId = adapterPosition + 1

            val skillItem = SkillItem(skillId, selectedStars)
            val existingSkillIndex = selectedSkillsList.indexOfFirst { it.skillId == skillId }

            if (existingSkillIndex != -1) {
                selectedSkillsList[existingSkillIndex] = skillItem
            } else {
                selectedSkillsList.add(skillItem)
            }
        }
    }

}
