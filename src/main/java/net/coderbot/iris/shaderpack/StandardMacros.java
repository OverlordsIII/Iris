package net.coderbot.iris.shaderpack;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.platform.GlDebugInfo;
import com.mojang.bridge.game.GameVersion;

import com.mojang.datafixers.util.Pair;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import net.minecraft.SharedConstants;
import net.minecraft.client.gl.GlDebug;
import net.minecraft.util.Util;

import net.fabricmc.loader.FabricLoader;
import net.fabricmc.loader.game.GameProvider;

public class StandardMacros {


	public static void addStandardMacros(List<String> lines) {
		String mcversion = getMcVersion();
		String os = getOsString();

		lines.add("#define MC_VERSION " + mcversion);
		lines.add("#define " + os);

	}

	/**
	 * Gets the current mc version String in a 5 digit format
	 *
	 * @see <a href="https://github.com/sp614x/optifine/blob/master/OptiFineDoc/doc/shaders.txt#L677">Optifine Doc</a>
	 *
	 * @return mc version string
	 */
	private static String getMcVersion() {
		String version = SharedConstants.getGameVersion().getReleaseTarget(); //release target so snapshots are set to the higher version

		StringBuilder builder = new StringBuilder();
		getGlVersion();
		boolean first = true;

		for (String semver : version.split("\\.")) { //split by dot
			int section = Integer.parseInt(semver);

			String versionString = Integer.toString(section);

			if (section < 10 && !first) { //dont add the zero if it is the very first number in "1.16"
				versionString = 0 + versionString;
			}

			first = false;

			builder.append(versionString);
		}
		return builder.toString();
	}

	private static String getOsString() {
		switch (Util.getOperatingSystem()) {
			case OSX:
				return "MC_OS_MAC";
			case LINUX:
				return "MC_OS_LINUX";
			case WINDOWS:
				return "MC_OS_WINDOWS";
			case SOLARIS: //Note: Optifine doesn't have a Macro for Solaris. https://github.com/sp614x/optifine/blob/master/OptiFineDoc/doc/shaders.txt#L689-L692
			case UNKNOWN:
			default:
				return "MC_OS_UNKNOWN";
		}
	}

	private static void getGlVersion() {
		System.out.println(GlDebugInfo.getVersion());
	}
}
