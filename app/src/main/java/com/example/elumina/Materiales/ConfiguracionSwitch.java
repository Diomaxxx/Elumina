package com.example.elumina.Materiales;

import android.content.res.ColorStateList;
import android.graphics.Color;

import com.google.android.material.materialswitch.MaterialSwitch;

public class ConfiguracionSwitch {
    /**
     * Configura un MaterialSwitch con colores personalizados: verde cuando está activado.
     *
     * @param materialSwitch Switch que se va a personalizar.
     */
    public static void configurarSwitchVerde(MaterialSwitch materialSwitch) {
        // Colores: el círculo siempre blanco, la pista verde claro cuando está activado
        ColorStateList thumbColors = ColorStateList.valueOf(Color.WHITE);
        ColorStateList trackColors = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},  // Estado activado
                        new int[]{-android.R.attr.state_checked}  // Estado desactivado
                },
                new int[]{
                        Color.parseColor("#4CAF50"),  // Verde claro cuando está activado
                        Color.parseColor("#D3D3D3")  // Plomo claro cuando está desactivado
                }
        );

        materialSwitch.setThumbTintList(thumbColors);
        materialSwitch.setTrackTintList(trackColors);
    }

    /**
     * Configura un MaterialSwitch con colores personalizados: rojo cuando está activado.
     *
     * @param materialSwitch Switch que se va a personalizar.
     */
    public static void configurarSwitchRojo(MaterialSwitch materialSwitch) {
        // Colores: el círculo siempre blanco, la pista roja cuando está activado
        ColorStateList thumbColors = ColorStateList.valueOf(Color.WHITE);
        ColorStateList trackColors = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},  // Estado activado
                        new int[]{-android.R.attr.state_checked}  // Estado desactivado
                },
                new int[]{
                        Color.parseColor("#F44336"),  // Rojo fuerte cuando está activado
                        Color.parseColor("#D3D3D3")  // Plomo claro cuando está desactivado
                }
        );

        materialSwitch.setThumbTintList(thumbColors);
        materialSwitch.setTrackTintList(trackColors);
    }

    /**
     * Configura un MaterialSwitch con colores personalizados: plomo oscuro cuando está activado.
     *
     * @param materialSwitch Switch que se va a personalizar.
     */
    public static void configurarSwitchPlomoOscuro(MaterialSwitch materialSwitch) {
        // Colores: el círculo siempre blanco, la pista plomo oscuro cuando está activado
        ColorStateList thumbColors = ColorStateList.valueOf(Color.WHITE);
        ColorStateList trackColors = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},  // Estado activado
                        new int[]{-android.R.attr.state_checked}  // Estado desactivado
                },
                new int[]{
                        Color.parseColor("#696969"),  // Plomo oscuro cuando está activado
                        Color.parseColor("#D3D3D3")  // Plomo claro cuando está desactivado
                }
        );

        materialSwitch.setThumbTintList(thumbColors);
        materialSwitch.setTrackTintList(trackColors);
    }
}