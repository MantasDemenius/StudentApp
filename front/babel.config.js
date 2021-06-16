module.exports = {
  presets: [
    ['module:metro-react-native-babel-preset'],
    [require.resolve('babel-preset-react-app/dependencies'), { helpers: true }]
  ],
  plugins: [
    ['@babel/plugin-transform-flow-strip-types'],
    ['@babel/plugin-proposal-class-properties'],
    ['react-native-reanimated/plugin']
  ]
};
