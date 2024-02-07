<template>
  <!-- <nav>
    <router-link to="/login">Login</router-link> |
    <router-link to="/main">Main</router-link>
  </nav> -->
  <router-view/>
</template>
<script setup lang="ts">
const debounce = (fn: any, delay: any) => {
  let timer: any = null
  return function () {
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    const context = this
    const args = arguments
    clearTimeout(timer)
    timer = setTimeout(function () {
      fn.apply(context, args)
    }, delay)
  }
}

const _ResizeObserver = window.ResizeObserver
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor (callback: any) {
    callback = debounce(callback, 16)
    super(callback)
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

// nav {
//   padding: 30px;

//   a {
//     font-weight: bold;
//     color: #2c3e50;

//     &.router-link-exact-active {
//       color: #42b983;
//     }
//   }
// }
</style>
