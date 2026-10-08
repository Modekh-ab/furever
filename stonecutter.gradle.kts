plugins { id("dev.kikugie.stonecutter") }
stonecutter active "1.21.1"
stonecutter parameters { constants["modern_draw_texture"] = current.parsed >= "1.21.2" }
