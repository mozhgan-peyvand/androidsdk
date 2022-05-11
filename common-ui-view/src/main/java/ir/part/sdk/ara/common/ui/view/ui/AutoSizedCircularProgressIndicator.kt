/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.part.sdk.ara.common.ui.view.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import ir.part.sdk.ara.common.ui.resource.R

@Composable
fun AutoSizedCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary
) {
    BoxWithConstraints(modifier) {
        val diameter = with(LocalDensity.current) {
            // We need to minus the padding added within CircularProgressIndicator
            min(constraints.maxWidth.toDp(), constraints.maxHeight.toDp()) - InternalPadding
        }

        CircularProgressIndicator(
            strokeWidth = (diameter * StrokeDiameterFraction).coerceAtLeast(dimensionResource(id = R.dimen.spacing_quarter_base)),
            color = color
        )
    }
}

// Default stroke size
private val DefaultStrokeWidth = 4.dp
// Preferred diameter for CircularProgressIndicator
private val DefaultDiameter = 40.dp
// Internal padding added by CircularProgressIndicator
private val InternalPadding = 4.dp

private val StrokeDiameterFraction = DefaultStrokeWidth / DefaultDiameter

@Preview
@Composable
fun PreviewAutoSizedCircularProgressIndicator() {
    Surface {
        Column {
            AutoSizedCircularProgressIndicator(
                modifier = Modifier.size(dimensionResource(id = R.dimen.spacing_4x))
            )

            AutoSizedCircularProgressIndicator(
                modifier = Modifier.size(dimensionResource(id = R.dimen.spacing_6x))
            )

            AutoSizedCircularProgressIndicator(
                modifier = Modifier.size(dimensionResource(id = R.dimen.spacing_12x))
            )

            AutoSizedCircularProgressIndicator(
                modifier = Modifier.size(dimensionResource(id = R.dimen.spacing_16x))
            )

            AutoSizedCircularProgressIndicator(
                modifier = Modifier.size(dimensionResource(id = R.dimen.spacing_32x))
            )
        }
    }
}
