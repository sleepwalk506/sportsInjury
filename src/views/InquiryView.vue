<template>
  <div class="inquiry-view">
    <div class="inquiry-card">
      <div ref="sceneContainer" class="scene-container">
        <el-card v-if="showTooltip || isHovered || isHovered2" class="tooltip"
          v-bind:style="{ left: tooltipPosition.x + 'px', top: tooltipPosition.y + 'px' }"
          @mouseenter="isHovered = true, isHovered2 = false" @mouseleave="isHovered = false">
          <template #header>
            <div v-if="currentPart[0].injurySubType == null" class="tooltip-title">
              <span>{{ currentPart[0].injuryType }}</span>
            </div>
            <div v-else class="tooltip-title">
              <el-select v-model="selectedInjuryIndex" class="m-2" placeholder="select" size="large"
                @visible-change="isHovered2 = true">
                <el-option v-for="(item, index) in currentPart " :key="index" :label="item.injurySubType"
                  :value="index" />
              </el-select>
            </div>
          </template>
          <div class="tooltip-description">
            {{ formatDescription(currentPart[selectedInjuryIndex].injuryDescription) }}
          </div>
        </el-card>
        <el-button @click="goBack" class="back-to-main-inquiry">Back to Main Menu</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import * as THREE from 'three'
import { OBJLoader } from 'three/examples/jsm/loaders/OBJLoader.js'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js'
import { ElMessageBox } from 'element-plus'
import axios from 'axios'
import { injuryInquiryService } from '@/api/inquiry.js'

const getModelNameFromPath = (path) => {
  const parts = path.split('/')
  return parts[parts.length - 1].split('.')[0]
}

export default {
  name: 'InquiryView',
  setup() {
    const apiUrl = 'http://localhost:8082/injuryInquiry'
    const sceneContainer = ref(null)
    const tooltipPosition = ref({ x: 0, y: 0 })
    let scene, camera, renderer, model, controls
    const showTooltip = ref(false)
    const selectedInjuryIndex = ref('')
    const isHovered = ref(false)
    const isHovered2 = ref(false)
    const currentPart = ref({})
    const showInjurySubTypes = ref(false) // 锟斤拷锟斤拷锟角凤拷锟斤拷示 injurySubTypes 锟叫憋拷
    const models = ['/humanModel/Ankle Joint.obj', '/humanModel/Elbow Joint.obj', '/humanModel/Foot.obj', '/humanModel/Forearm.obj', '/humanModel/hand.obj', '/humanModel/Head.obj', '/humanModel/Knee.obj', '/humanModel/Lower Leg.obj', '/humanModel/Lumbar Vertebrae.obj', '/humanModel/Neck.obj', '/humanModel/Sacrum and Coccyx.obj', '/humanModel/Shoulder.obj', '/humanModel/Thigh.obj', '/humanModel/Thoracic Vertebrae.obj', '/humanModel/Upper Limbs.obj', '/humanModel/Wrist.obj']

    const initScene = () => {
      scene = new THREE.Scene()
      camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000)
      renderer = new THREE.WebGLRenderer({ alpha: true })
      renderer.setSize(window.innerWidth, window.innerHeight)
      // 使锟矫憋拷锟斤拷锟芥储 DOM 元锟截ｏ拷锟斤拷锟斤拷锟轿诧拷询
      const container = sceneContainer.value
      container.appendChild(renderer.domElement)
      // 锟斤拷锟斤拷锟斤拷锟狡斤拷泄锟�
      const leftDirectionalLight = new THREE.DirectionalLight(0xffffff, 2.5)
      leftDirectionalLight.position.set(-1, 1, 0) // 锟斤拷锟斤拷锟斤拷锟斤拷锟�
      scene.add(leftDirectionalLight)

      // 锟斤拷锟斤拷锟揭诧拷平锟叫癸拷
      const rightDirectionalLight = new THREE.DirectionalLight(0xffffff, 2.5)
      rightDirectionalLight.position.set(1, 1, 0) // 锟斤拷锟揭诧拷锟斤拷锟斤拷
      scene.add(rightDirectionalLight)

      const loader = new OBJLoader()
      models.forEach(modelpath => {
        loader.load(modelpath, (object) => {
          model = object
          // 锟斤拷锟斤拷模锟酵碉拷锟斤拷锟斤拷
          model.name = getModelNameFromPath(modelpath)
          // console.log(model.name)
          // 锟斤拷锟斤拷模锟酵的诧拷锟斤拷
          model.traverse((child) => {
            if (child instanceof THREE.Mesh) {
              // 锟斤拷锟斤拷锟接讹拷锟斤拷锟斤拷锟斤拷锟�
              child.name = modelpath.split('/')[2].split('.')[0]
              // 锟斤拷锟斤拷锟接讹拷锟斤拷牟锟斤拷锟�
              child.material = new THREE.MeshStandardMaterial({ color: 0xffffff })
            }
          })
          // 锟斤拷锟斤拷模锟酵的达拷小
          model.scale.set(2, 2, 2)
          // 锟斤拷锟斤拷模锟酵碉拷位锟斤拷
          model.position.set(0, -1, 0)
          // 锟斤拷模锟斤拷锟斤拷锟接碉拷锟斤拷锟斤拷锟斤拷
          scene.add(model)
        }
        )
      })
      camera.position.z = 5

      controls = new OrbitControls(camera, renderer.domElement)

      container.addEventListener('mousemove', onMouseMove)

      const animate = () => {
        requestAnimationFrame(animate)
        controls.update()
        renderer.render(scene, camera)
      }
      animate()
    }

    const onMouseMove = async (event) => {
      // 锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷投锟斤拷锟斤拷
      const raycaster = new THREE.Raycaster()
      // 锟斤拷锟斤拷一锟斤拷 Three.js 锟斤拷锟斤拷锟斤拷锟斤拷示锟斤拷锟轿伙拷锟�
      const mouse = new THREE.Vector2()
      // 锟斤拷锟斤拷锟斤拷示锟斤拷锟轿伙拷锟�
      console.log('1:', isHovered.value)
      console.log('2:', isHovered2.value)
      if (!isHovered.value) tooltipPosition.value = { x: event.offsetX, y: event.offsetY }
      // 锟斤拷锟斤拷锟轿伙拷锟阶�锟斤拷为锟斤拷准锟斤拷锟借备锟斤拷锟斤拷
      mouse.x = (event.offsetX / window.innerWidth) * 2 - 1
      mouse.y = -(event.offsetY / window.innerHeight) * 2 + 1

      // 锟斤拷锟斤拷锟斤拷锟斤拷投锟斤拷锟斤拷锟斤拷锟斤拷始锟斤拷头锟斤拷颍�伙拷锟斤拷锟斤拷锟轿伙拷煤锟斤拷锟斤拷锟斤拷
      raycaster.setFromCamera(mouse, camera)

      // 锟斤拷锟斤拷锟斤拷锟斤拷欠锟斤拷锟侥ｏ拷锟斤拷嘟�
      const intersects = raycaster.intersectObjects(scene.children, true)
      if (intersects.length > 0) {
        const currentDesc = { injuryType: intersects[0].object.name }
        try {
          // const response = await axios.post(apiUrl, currentDesc)
          const response = await injuryInquiryService(currentDesc);
          currentPart.value = response.data.data
          selectedInjuryIndex.value = 0
          // currentPart.value.injuryDescription = response.data.data[0].injuryDescription
          // currentPart.value.injurySubType = response.data.data[0].injurySubType
          // console.log(currentPart.value)
        } catch (error) {
          console.error(error.response)
        }
        showTooltip.value = true
      } else {
        showTooltip.value = false
      }
    }
    const router = useRouter()
    const goBack = () => {
      router.push('/main')
    }

    onMounted(() => {
      initScene()
    })
    const formatDescription = (description) => {
      const wordsPerLine = 5 // 每5锟斤拷锟斤拷锟绞诧拷锟斤拷一锟斤拷锟斤拷锟叫凤拷
      const words = description.split(/\s+/) // 使锟斤拷锟斤拷锟斤拷锟斤拷锟绞斤拷锟斤拷谋锟斤拷指锟轿�锟斤拷锟斤拷锟斤拷锟斤拷
      // 锟斤拷锟诫换锟叫凤拷锟斤拷位锟斤拷
      for (let i = wordsPerLine; i < words.length; i += wordsPerLine + 1) {
        words.splice(i, 0, '\n')
      }
      return words.join(' ') // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷为锟街凤拷锟斤拷
    }
    return {
      sceneContainer,
      showTooltip,
      currentPart,
      goBack,
      tooltipPosition,
      formatDescription,
      isHovered,
      isHovered2,
      selectedInjuryIndex
    }
  }
}
</script>

<style scoped>
.inquiry-view {
  background-color: white;
  color: #fff;
  text-align: center;
  font-size: 18px;
  padding: 20px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.inquiry-card {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
}

.scene-container {
  position: relative;
  margin-top: 30px;
  /* justify-content: center; */
}

.tooltip-title {
  font-weight: bold;
  /* 锟接粗憋拷锟斤拷锟侥憋拷 */
  font-size: 18px;
  /* 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟叫� */
}

.back-to-main-inquiry {
  display: flex;
  position: fixed;
  /* 鍥哄畾鍦ㄩ〉闈�涓婃柟 */
  top: 0;
  /* 璺濈�婚〉闈㈤《閮ㄧ殑璺濈�� */
  left: 45%;
  /* 璺濈�婚〉闈㈠乏渚х殑璺濈�� */
  /* padding: 10px 0; */
  /* box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); */
}

.tooltip-description {
  font-size: 16px;
  word-wrap: break-word;
  /* 锟斤拷锟斤拷锟斤拷锟绞伙拷锟斤拷 */
  white-space: pre-line;
  /* 锟斤拷锟斤拷锟秸革拷突锟斤拷蟹锟� */
}

.tooltip {
  position: absolute;
  background-color: #f0f0f0;
  /* 锟斤拷锟斤拷色锟斤拷锟斤拷 */
  color: #333;
  /* 锟侥憋拷锟斤拷色 */
  border: 1px solid #a0a0a0;
  /* 浅锟斤拷色锟竭匡拷 */
  border-radius: 10px;
  /* 圆锟斤拷 */
  padding: 10px;
  /* 锟节边撅拷 */
  font-size: 16px;
  /* 锟斤拷锟斤拷锟叫� */
}

.m2 {
  font-weight: bold !important;
  /* 锟接粗憋拷锟斤拷锟侥憋拷 */
  font-size: 18px !important;
  /* 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟叫� */
  color: black !important;
  /* 锟斤拷锟斤拷锟斤拷色为锟斤拷色 */
  background-color: #f0f0f0 !important;
  /* 浅锟斤拷色锟斤拷锟斤拷 */
  padding: 10px !important;
  /* 锟节边撅拷 */
  border-radius: 5px !important;
  /* 圆锟斤拷 */
}
</style>
