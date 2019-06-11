package org.asyou.sequoia.dao;

public class SequoiaAutoCreate {

    public static void setAll(boolean autoCreate){
        find.setAll(autoCreate);
        insert.setAll(autoCreate);
        update.setAll(autoCreate);
        delete.setAll(autoCreate);
    }

    public static final Find find = new Find();
    public static final Insert insert = new Insert();
    public static final Update update = new Update();
    public static final Delete delete = new Delete();


    public static class Find{
        private boolean autoCreateSapce = false;
        private boolean autoCreateCollection = false;

        public boolean isAutoCreateSapce() {
            return autoCreateSapce;
        }

        public void setAutoCreateSapce(boolean autoCreateSapce) {
            this.autoCreateSapce = autoCreateSapce;
        }

        public boolean isAutoCreateCollection() {
            return autoCreateCollection;
        }

        public void setAutoCreateCollection(boolean autoCreateCollection) {
            this.autoCreateCollection = autoCreateCollection;
        }

        public void setAll(boolean autoCreate){
            this.setAutoCreateSapce(autoCreate);
            this.setAutoCreateCollection(autoCreate);
        }
    }

    public static class Insert{
        private boolean autoCreateSapce = false;
        private boolean autoCreateCollection = false;

        public boolean isAutoCreateSapce() {
            return autoCreateSapce;
        }

        public void setAutoCreateSapce(boolean autoCreateSapce) {
            this.autoCreateSapce = autoCreateSapce;
        }

        public boolean isAutoCreateCollection() {
            return autoCreateCollection;
        }

        public void setAutoCreateCollection(boolean autoCreateCollection) {
            this.autoCreateCollection = autoCreateCollection;
        }

        public void setAll(boolean autoCreate){
            this.setAutoCreateSapce(autoCreate);
            this.setAutoCreateCollection(autoCreate);
        }
    }

    public static class Update{
        private boolean autoCreateSapce = false;
        private boolean autoCreateCollection = false;

        public boolean isAutoCreateSapce() {
            return autoCreateSapce;
        }

        public void setAutoCreateSapce(boolean autoCreateSapce) {
            this.autoCreateSapce = autoCreateSapce;
        }

        public boolean isAutoCreateCollection() {
            return autoCreateCollection;
        }

        public void setAutoCreateCollection(boolean autoCreateCollection) {
            this.autoCreateCollection = autoCreateCollection;
        }

        public void setAll(boolean autoCreate){
            this.setAutoCreateSapce(autoCreate);
            this.setAutoCreateCollection(autoCreate);
        }
    }

    public static class Delete{
        private boolean autoCreateSapce = false;
        private boolean autoCreateCollection = false;

        public boolean isAutoCreateSapce() {
            return autoCreateSapce;
        }

        public void setAutoCreateSapce(boolean autoCreateSapce) {
            this.autoCreateSapce = autoCreateSapce;
        }

        public boolean isAutoCreateCollection() {
            return autoCreateCollection;
        }

        public void setAutoCreateCollection(boolean autoCreateCollection) {
            this.autoCreateCollection = autoCreateCollection;
        }

        public void setAll(boolean autoCreate){
            this.setAutoCreateSapce(autoCreate);
            this.setAutoCreateCollection(autoCreate);
        }
    }
}
