const goodsList = [
  {
    goodsId: 0,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/1.png",
    title: " 李宁速干T恤【线下同款】男运动短袖上衣吸汗透气跑步纯色体恤黑色",
    price: 49.00,
    number: 1
  },
  {
    goodsId: 1,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/2.png",
    title: "李宁（LI-NING）瑜伽垫男士健身运动垫子加厚家用防滑跳绳隔音减震仰卧起坐舞蹈垫",
    price: 45.00,
    number: 1
  },
  {
    goodsId: 2,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/3.png",
    title: "特步（XTEP）跳绳垫瑜伽隔音减震垫静音室内健身防滑缓冲垫无绳跳操垫地垫毯子",
    price: 39.90,
    number: 1
  },
  {
    goodsId: 3,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/13.png",
    title: "回力（Warrior）官方运动鞋男鞋夏季轻便跑步鞋男透气百搭网鞋软底网面鞋男飞织鞋",
    price: 109,
    number: 1
  },
  {
    goodsId: 4,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/4.png",
    title: "回力（Warrior）官方运动鞋子男鞋秋季新潮百搭复古滑板鞋网面透气休闲鞋厚底增高",
    price: 109.90,
    number: 1
  },
  {
    goodsId: 5,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/5.png",
    title: "李宁（LI-NING） 竞速跳绳减肥健身燃脂运动学生成人男女绳子中考网红室内跳神训练可调节家用跳绳 雅黑【竞速手柄\\绳长2.6m】",
    price: 12,
    number: 1
  },
  {
    goodsId: 6,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/6.png",
    title: "李宁【两只装】护腕男女运动健身吸汗防扭伤护手腕护具篮球羽毛球擦汗",
    price: 26,
    number: 1
  },
  {
    goodsId: 7,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/7.png",
    title: "唐麦H5头戴主动降噪蓝牙耳机手机电脑平板通用运动音乐游戏耳机无线耳麦适用于苹果华为小米 星河银",
    price: 499,
    number: 1
  },
  {
    goodsId: 8,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/8.png",
    title: "李宁筋膜球肌肉放松瑜伽按摩运动健身球手握足底按摩经膜球",
    price: 26,
    number: 1
  },
  {
    goodsId: 9,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/9.png",
    title: "李宁（LI-NING）运动发带男女吸汗头带止汗带跑步导汗带篮球防汗头巾戴束发带头箍",
    price: 18.90,
    number: 1
  },
  {
    goodsId: 10,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/10.png",
    title: "李宁（LI-NING）运动护膝半月板篮球专业羽毛球膝盖护具夏季跑步健身登山髌骨",
    price: 175,
    number: 1
  },
  {
    goodsId: 11,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/11.png",
    title: "小米（MI）手环8 NFC版 150种运动模式 血氧心率睡眠监测 多样快拆腕带 小米手环 智能手环 运动手环 亮黑色",
    price: 259,
    number: 1
  },
  {
    goodsId: 12,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/12.png",
    title: "小特 哑铃男士健身家用杠铃套装可拆卸环保包胶手铃亚玲家用运动锻炼 20公斤",
    price: 45,
    number: 1
  },
  {
    goodsId: 13,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/13.png",
    title: "李宁复古跑 男鞋春秋低帮透气情侣款复古撞色设计运动鞋ARLS005",
    price: 218,
    number: 1
  },
  {
    goodsId: 14,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/14.png",
    title: "漫步者（EDIFIER）漫步者（EDIFIER）Retro Pro花再冇心真蓝牙耳机 无线主动降噪入耳式耳机 适用于苹果安卓 Retro Pro暮蓝+背包鸭保护套",
    price: 348,
    number: 1
  },
  {
    goodsId: 15,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/15.png",
    title: "李宁 运动护腕 扭伤护手腕腱鞘护具护手腕套男女健身擦汗吸汗擦汗篮球羽毛球跑步手腕棉质擦汗巾 010黑一只装",
    price: 13.80,
    number: 1
  },
  {
    goodsId: 16,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/16.png",
    title: "迪舒（DISHU）护膝运动健身篮球跑步深蹲专业半月板防护男女跳绳登山护具 两只装",
    price: 89.90,
    number: 1
  },
  {
    goodsId: 17,
    picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/17.png",
    title: "李宁启星2.0 复古老爹鞋轻弹运动鞋2023厚底鞋情侣款男鞋AGCT311",
    price: 338,
    number: 1
  },
  // {
  //   goodsId: ,
  //   picture: "https://mp-3b164b9b-3af2-416f-ad03-672245f15dd7.cdn.bspapp.com/shop/.png",
  //   title: "",
  //   price: ,
  //   number: 0
  // },
]
export default goodsList;
