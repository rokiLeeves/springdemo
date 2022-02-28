package com.myself.springdemo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

@Slf4j
public class MybatisplusFillHandler implements MetaObjectHandler {



        /**
         * 插入元对象字段填充（用于插入时对公共字段的填充）
         *
         * @param metaObject 元对象
         */
        @Override
        public void insertFill(MetaObject metaObject) {
            log.info("date append");
            this.strictInsertFill(metaObject, "creationDate", LocalDateTime::now, LocalDateTime.class);
            this.strictInsertFill(metaObject, "email", ()->"default@example.com",String.class );
            this.strictInsertFill(metaObject, "version", ()->1,Integer.class );

        }

        /**
         * 更新元对象字段填充（用于更新时对公共字段的填充）
         *
         * @param metaObject 元对象
         */
        @Override
        public void updateFill(MetaObject metaObject) {
            this.strictUpdateFill(metaObject, "updateDate", LocalDateTime::now, LocalDateTime.class);
        }

}
