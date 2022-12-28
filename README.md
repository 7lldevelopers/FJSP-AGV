# FJSP-AGV



**\*柔性作业车间生产调度\***

​      在传统的柔性作业车间调度问题研究中，工件在各加工机器间的配送时间和AGV在调度中的路径冲突和路径选择以及有限数量AGV的分配调度常被研究者忽略不计，但这种假设与实际情况不相符合，尤其是依赖多AGV在不同加工机器间时间、距离、多批量的配送下，AGV的运输时间和路径冲突以及订单的分配情况在求解最优调度方案的过程中不容忽视。一方面，改变企业由于制造过程(生产与运输)协同性低导致的生产结构不合理、效率低、能耗大等问题，可以提升企业的核心竞争力，优化车间的制造工艺，减少生产环节的成本消耗，促进企业实现绿色转型；另一方面，生产要素(加工、运输、辅助设备等)的利用直接影响着制造车间生产效益，社会因素(国家政策、社会条件和企业形象等)的存在也会影响着企业生产决策。

​      因此，为了保证各道工序顺畅衔接以及加工机器的工作效率，AGV物流与生产协同调度需基于生产订单计划决策多台AGV 运送订单任务的顺序、规划运输路线、解决碰撞，最终实现最小化订单的最大完成制造时间。

![image-20221228145846631](README.assets/image-20221228145846631.png)

![image-20221228150001064](README.assets/image-20221228150001064.png)

![image-20221228150035862](README.assets/image-20221228150035862.png)

![image-20221228150109988](README.assets/image-20221228150109988.png)

集成AGV调度与生产调度的柔性作业车间问题



## 问题描述:

1.  订单需要在一组机器上面完成一组工件的加工
2. 每个工件的加工包含多道工序
3. 工序之间需满足一定的顺序约束
4. 每道工序只需要一台机器进行加工
5.  工序存在并行的加工机器 
5. 一台机器只能加工一道工序

## 决策变量

 	订单顺序
 	AGV分配到的订单
 	每台机器上工件的加工顺序

![image-20221228150408728](README.assets/image-20221228150408728.png)

# 算法

## 传统的遗传算法：



![image-20221228150719286](README.assets/image-20221228150719286.png)

## 量子进化算法 QEA Quantum-Inspired Evolutionary Algorithm

![image-20221228150725608](README.assets/image-20221228150725608.png)

![image-20221228150750396](README.assets/image-20221228150750396.png)