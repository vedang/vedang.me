module.exports = {
  darkMode: "class",
  content: ["./target/public/*.{html,js}"],
  safelist: ['dark'],
  theme: {
    extend: {},
    fontFamily: {
      sans: ["Fira Sans", "-apple-system", "BlinkMacSystemFont", "sans-serif"],
      serif: ["PT Serif", "serif"],
      mono: ["Fira Mono", "monospace"]
    }
  },
  plugins: [require("@tailwindcss/typography")],
};
