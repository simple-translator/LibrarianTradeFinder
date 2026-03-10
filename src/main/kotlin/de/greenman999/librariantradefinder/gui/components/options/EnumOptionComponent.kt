/*
 * A minecraft mod that helps you find the enchantments you need from a Librarian Villager.
 * Copyright (C) 2026. Greenman999
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package de.greenman999.librariantradefinder.gui.components.options

import de.greenman999.librariantradefinder.util.translatable
import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.elementa.dsl.effect
import gg.essential.elementa.dsl.pixels
import gg.essential.elementa.dsl.provideDelegate
import gg.essential.elementa.effects.OutlineEffect
import java.awt.Color

class EnumOptionComponent<T : Enum<T>>(optionKey: String, enum: Class<T>, initialValue: T, initialDisabled: Boolean = false, showTooltip: Boolean = true) : OptionComponent(optionKey, initialDisabled, showTooltip) {

	var onUpdateListener: UIComponent.(T) -> Unit = {}

	private val values: Array<T> = enum.enumConstants
	private var value: T = initialValue

	val enumSelector by UIBlock(Color.DARK_GRAY).constrain {
		x = 0.pixels(alignOpposite = true)
		y = CenterConstraint()
		width = 90.pixels()
		height = 18.pixels()
	} childOf this effect OutlineEffect(Color.GRAY, 1f)

	val enumSelectorText by UIText(translatable("librariantradefinder.gui.options." + optionKey + "." + value.name.lowercase())).constrain {
		x = CenterConstraint()
		y = CenterConstraint()
	} childOf enumSelector

	init {
		enumSelector.onMouseClick {
			val currentIndex = values.indexOf(value)
			val nextIndex = (currentIndex + 1) % values.size
			value = values[nextIndex]
			onUpdateListener(value)
			enumSelectorText.setText(translatable("librariantradefinder.gui.options." + optionKey + "." + value.name.lowercase()))
		}
	}

	fun onUpdate(callback: UIComponent.(T) -> Unit) = apply {
		onUpdateListener = callback
	}
}
