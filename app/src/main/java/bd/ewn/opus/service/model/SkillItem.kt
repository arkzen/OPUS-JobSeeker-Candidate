package bd.ewn.opus.service.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SkillItem(val skillId: Int, val skillLevel: Int):Parcelable

