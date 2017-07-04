package com.bobo.something.data;

import java.io.Serializable;
import java.util.List;

public class ProvinceCityBean implements Serializable {

    private int version;
    private List<DataBean> list;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<DataBean> getList() {
        return list;
    }

    public void setList(List<DataBean> list) {
        this.list = list;
    }

    public static class DataBean implements Serializable {

        private String groupName;
        private List<AreaBean> provinces;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public List<AreaBean> getProvinces() {
            return provinces;
        }

        public void setProvinces(List<AreaBean> provinces) {
            this.provinces = provinces;
        }

        public static class AreaBean implements Serializable {
            private int id;
            private String name;
            private int parentId;
            private int locationGroupID;
            private boolean isLabel;
            private List<AreaBean> citys;

            public int getLocationGroupID() {
                return locationGroupID;
            }

            public void setLocationGroupID(int locationGroupID) {
                this.locationGroupID = locationGroupID;
            }


            public boolean isLabel() {
                return isLabel;
            }

            public void setLabel(boolean label) {
                isLabel = label;
            }

            public int getId() {
                return id;
            }

            public void setId(int Id) {
                this.id = Id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public List<AreaBean> getCitys() {
                return citys;
            }

            public void setCitys(List<AreaBean> citys) {
                this.citys = citys;
            }

        }
    }
}
