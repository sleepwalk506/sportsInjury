<template>
  <div>
    <el-dialog
      v-model="dialogVisible"
      title="DoctorBot"
      align-center
      class="chat-container">
      <div class="body">
        <el-scrollbar class="chat-messages">
          <div v-for="message in messages" :key="message.id" class="message" >
            <div :class="{'message-bubble': true, 'from-sender': message.senderId === 1, 'from-receiver': message.senderId !== 1}">
              {{ message.content }}
            </div>
          </div>
        </el-scrollbar>
        <div class="chat-input">
          <el-input class="el-input" v-model="newMessage" placeholder="输入一条消息"></el-input>
          <el-button class="el-button" @click="send">发 送</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  import { diseaseData, positionData } from '@/data/injury'
  const newMessage =  ref('')
  const messages = ref([
    {
      senderId: 0,
      content: '你好，我是你的AI医生，有什么需要帮助的？'
    }
  ])
  const dialogVisible = ref(false)
  mounted: {
    console.log('')
  }

  const show = function () {
    dialogVisible.value = true
  }
  const close = function () {
    dialogVisible.value = false
  }
  const send = function () {
    const quest = newMessage.value
    messages.value.push({
      senderId: 1,
      content: quest
    })
    newMessage.value = ""
    reply(quest)
  }
  const reply = function (quest: string) {
    let answer = ""

    const r_how = /怎么/
    const r_why = /为什么|原因|什么导致|什么会导致/
    const r_what = /什么/
    const r_whatis = /什么是|是什么/
    const r_performance = /表现|现象|症状|表征/
    const r_howtodo = /怎么办|怎么做|怎么处理|治疗/
    const r_hurt = /疼|痛|病/
    let get_disease_flag = false
    // 根据疾病名查询疾病
    for (const diseaseKey in diseaseData) {
      const disease = diseaseData[diseaseKey];
      const r_disease = new RegExp(diseaseKey);
      let flag = false

      if (r_disease.test(quest)) {
        get_disease_flag = true
        if (r_whatis.test(quest)) {
          answer = disease.description
          flag = true
        }
        if (r_why.test(quest)) {
          answer += flag ? "\n"+disease.reason:disease.reason
          flag = true
        }
        if (r_performance.test(quest)) {
          answer += flag ? "\n"+disease.performance:disease.performance
          flag = true
        }
        if (r_howtodo.test(quest)) {
          answer += flag ? "\n"+disease.treatment:disease.treatment
          flag = true
        }
        if (!flag) {
          answer = disease.description+"\n"+disease.reason+"\n"+disease.performance+"\n"+disease.treatment
        }
      }
    }
    // 根据部位查询疾病
    if (!get_disease_flag && r_hurt.test(quest)) {
      let quest_position = ""
      let diseases = []
      for (const positionsKey in positionData) {
        for (const positionKey in positionData[positionsKey]) {
          // 查询quest中的部位
          const position = positionData[positionsKey][positionKey]
          let r_position = new RegExp(position, "g")
          if (r_position.test(quest)) {
            let matches = quest.match(r_position)
            if (matches)
              for (const m of matches) {
                if (m!="")
                  quest_position = m
              }
              for (const diseaseKey in diseaseData) {
                if (r_position.test(diseaseKey))
                  diseases.push(diseaseKey)
              }
              if (diseases.length>0) {
                get_disease_flag = true
                let diseaseString = diseases.join("、")
                if (diseases.length == 1) {
                  answer = quest_position+"疼痛可能与"+diseaseString+"有关。"
                }else {
                  answer = quest_position+"疼痛的原因有很多。可能与"+diseaseString+"等疾病有关。"
                }
              }
          }
        }
      }
      if (!get_disease_flag ) {
        if (quest_position != "")
          answer = "抱歉，"+quest_position+"疼痛不是我擅长领域。请去医院找专业的医生咨询。"
        else
          answer = "抱歉，您所咨询的疾病不是我擅长领域。请去医院找专业的医生咨询。"
      }
    }else if(!get_disease_flag) {
      answer = "抱歉，我没明白您的意思。我是AI医生，如果有关于疾病的问题可以向我提问。"
    }
    messages.value.push({
      senderId: 0,
      content: answer
    })
  }

  defineExpose({
    show
  });
</script>

<style scoped>
*{
  margin: 0;
  padding: 0;
}
.chat-container {
  background-color: #f0f2f5;
}


.body {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 92vh;
  position: relative;
}

.chat-messages {
  flex: 1;
  margin-right: -20px;
  overflow-y: scroll;
  width: 100%; /* Adjust this to accommodate the remaining space */
  float: left; /* Add this line to make it float left */
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  margin-bottom: 75px;
  height: 100vh;
}
.message {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 10px;
  white-space: pre-line;
}

.message-bubble {
  /* align-content: flex-start; */
  text-align: left;
  background-color: #fff;
  border-radius: 15px;
  padding: 10px 15px;
  max-width: 70%;
  word-wrap: break-word;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
}

.from-sender {
  align-self: flex-end;
  background-color: #fff;
}

.from-receiver {
  align-self: flex-start;
  background-color: #DCF8C6;
}

.chat-input {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: white;
  border-top: 1px solid #ccc;
  position: absolute;
  bottom: 0;
  width: 100%;
}

.el-input {
  flex: 1;
  resize: none;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.el-button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 12px;
  margin-left: 10px;
  cursor: pointer;
}

</style>

<style>
.chat-container .el-dialog__body {
  padding: 0;
}
</style>
